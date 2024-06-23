package io.travel_tunes.ui.fragments.payments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.travel_tunes.R
import io.travel_tunes.appComponent
import io.travel_tunes.data.remote.Resource
import io.travel_tunes.databinding.FragmentPaymentsBinding
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.model.payments.PaymentVariantsParcelable
import io.travel_tunes.utils.FragmentResultUtils
import io.travel_tunes.utils.adapters.PaymentVariantAdapter
import io.travel_tunes.utils.base.BaseBottomSheetDialogFragment
import io.travel_tunes.utils.extencions.parcelable
import io.travel_tunes.utils.payment.UkassaHelper
import javax.inject.Inject

class PaymentsFragment : BaseBottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactoryInner: PaymentsViewModelFactory.Factory
    private lateinit var viewModel: PaymentsViewModel

    private lateinit var paymentVariantAdapter: PaymentVariantAdapter
    private lateinit var binding: FragmentPaymentsBinding

    companion object {
        private const val EXTRA_PAYMENT_VARIANTS = "payment_variants"
        fun getInstance(paymentVariants: List<PaymentVariant>): PaymentsFragment {
            val args = Bundle()
            val fragment = PaymentsFragment()
            args.putParcelable(EXTRA_PAYMENT_VARIANTS, PaymentVariantsParcelable(paymentVariants))
            fragment.arguments = args
            return fragment
        }
    }

    private val tokenizeLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    // show token = result.data
                    handleTokenizeSuccess(result.data)
                }

                Activity.RESULT_CANCELED -> {
                    //show error
                    showTokenizeError()
                }
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        binding.dialogClose.setOnClickListener { dismiss() }
        binding.dialogInfo.setOnClickListener {
            setFragmentResult(
                FragmentResultUtils.REQUEST_OPEN_OFFER_AGREEMENTS,
                bundleOf(FragmentResultUtils.BUNDLE_OPEN_OFFER_AGREEMENTS to true)
            )
        }
        paymentVariantAdapter = PaymentVariantAdapter { paymentVariant ->
            viewModel.handleOnPaymentsAdapterClick(paymentVariant)
            startTokenizeInit(paymentVariant)
        }
        binding.rvPayments.adapter = paymentVariantAdapter
    }

    private fun initViewModel() {
        val paymentVariants = arguments?.parcelable<PaymentVariantsParcelable>(
            EXTRA_PAYMENT_VARIANTS
        )?.getPaymentVariants() ?: return
        val viewModelFactory = viewModelFactoryInner.create(paymentVariants)
        viewModel = ViewModelProvider(this, viewModelFactory)[PaymentsViewModel::class.java]

        viewModel.paymentVariantsLiveData.observe(viewLifecycleOwner) { variants ->
            paymentVariantAdapter.updateData(variants)
        }

        viewModel.progressDialogText.observe(viewLifecycleOwner) {
            handleMessageProgress(it, requireContext())
        }

        viewModel.sendPaymentsResult.observe(viewLifecycleOwner) { result ->
            if (result == null) return@observe
            when (result) {
                is Resource.Success -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.payment_success_title)
                        .setMessage(R.string.payment_success_message)
                        .setPositiveButton(
                            R.string.payment_success_positive_btn
                        ) { _, _ ->
                            this.dismiss()
                        }
                        .show()
                }

                is Resource.Failure -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setMessage(R.string.payment_error_message)
                        .setPositiveButton(
                            R.string.payment_error_positive_btn
                        ) { _, _ -> }
                        .show()
                }

                else -> {}
            }
        }
    }

    private fun startTokenizeInit(paymentVariant: PaymentVariant) {
        val intentTokenize =
            UkassaHelper.generateIntentForTokenize(requireContext(), paymentVariant)
        tokenizeLauncher.launch(intentTokenize)
    }

    private fun handleTokenizeSuccess(data: Intent?) {
        if (data == null) {
            showTokenizeError()
        } else {
            val token = UkassaHelper.getTokenFromResult(data)
            viewModel.saveTokenResult(token)
            viewModel.sendPayment()
        }
    }

    private fun showTokenizeError() {
        Toast.makeText(requireContext(), R.string.tokenization_canceled, Toast.LENGTH_SHORT).show()
    }

}