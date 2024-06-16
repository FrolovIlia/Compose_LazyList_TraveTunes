package io.travel_tunes.ui.fragments.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import io.travel_tunes.databinding.FragmentPaymentsBinding
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.model.payments.PaymentVariantsParcelable
import io.travel_tunes.utils.FragmentResultUtils
import io.travel_tunes.utils.adapters.PaymentVariantAdapter
import io.travel_tunes.utils.base.BaseBottomSheetDialogFragment
import io.travel_tunes.utils.extencions.parcelable
import io.travel_tunes.utils.prefs.PreferenceManager

class PaymentsFragment : BaseBottomSheetDialogFragment() {

    private lateinit var viewModelFactory: PaymentsViewModelFactory
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
        paymentVariantAdapter = PaymentVariantAdapter {
            Toast.makeText(
                binding.root.context,
                "${it.getName()} покупаем",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.rvPayments.adapter = paymentVariantAdapter
    }

    private fun initViewModel() {
        val paymentVariants = arguments?.parcelable<PaymentVariantsParcelable>(
            EXTRA_PAYMENT_VARIANTS
        )?.getPaymentVariants() ?: return
        val preferenceManager = PreferenceManager(requireContext())
        viewModelFactory = PaymentsViewModelFactory(paymentVariants, preferenceManager)
        viewModel = ViewModelProvider(this, viewModelFactory)[PaymentsViewModel::class.java]

        viewModel.paymentVariantsLiveData.observe(viewLifecycleOwner) { variants ->
            paymentVariantAdapter.updateData(variants)
        }
    }
}