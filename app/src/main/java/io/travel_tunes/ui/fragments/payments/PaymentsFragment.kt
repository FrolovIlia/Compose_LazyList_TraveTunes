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
import io.travel_tunes.databinding.FragmentPaymentsBinding
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.model.payments.PaymentVariantsParcelable
import io.travel_tunes.utils.CrashlyticsUtils
import io.travel_tunes.utils.FragmentResultUtils
import io.travel_tunes.utils.adapters.PaymentVariantAdapter
import io.travel_tunes.utils.base.BaseBottomSheetDialogFragment
import io.travel_tunes.utils.extencions.parcelable
import io.travel_tunes.utils.payment.UkassaHelper
import ru.yoomoney.sdk.kassa.payments.Checkout
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

    private val confirmationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    // Процесс 3ds завершён, нет информации о том, завершился процесс с успехом или нет
                    // Рекомендуется запросить статус платежа
                    viewModel.handleSuccessConfirmation()
                }

                Activity.RESULT_CANCELED -> {
                    // Экран 3ds был закрыт
                    CrashlyticsUtils.sendThrowableNonFatal(Throwable("confirmationLauncher Activity.RESULT_CANCELED"))
                    return@registerForActivityResult
                }

                Checkout.RESULT_ERROR -> {
                    // Во время 3ds произошла какая-то ошибка (нет соединения или что-то еще)
                    // Более подробную информацию можно посмотреть в data
                    // data.getIntExtra(Checkout.EXTRA_ERROR_CODE) - код ошибки из WebViewClient.ERROR_* или Checkout.ERROR_NOT_HTTPS_URL
                    // data.getStringExtra(Checkout.EXTRA_ERROR_DESCRIPTION) - описание ошибки (может отсутствовать)
                    // data.getStringExtra(Checkout.EXTRA_ERROR_FAILING_URL) - url по которому произошла ошибка (может отсутствовать)
                    val errorCode = result.data?.getIntExtra(Checkout.EXTRA_ERROR_CODE, -1)
                    val errorDescription =
                        result.data?.getStringExtra(Checkout.EXTRA_ERROR_DESCRIPTION)
                    val errorFailingUrl =
                        result.data?.getStringExtra(Checkout.EXTRA_ERROR_FAILING_URL)
                    CrashlyticsUtils.sendThrowableNonFatal(Throwable("confirmationLauncher Checkout.RESULT_ERROR errorCode = $errorCode, errorDescription = $errorDescription, errorFailingUrl = $errorFailingUrl"))
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

        viewModel.paymentsConfirmationIntent.observe(viewLifecycleOwner) { confirmationIntent ->
            if (confirmationIntent != null) {
                viewModel.clearPaymentsConfirmationIntent()
                val intentConfirmation =
                    UkassaHelper.getConfirmationIntent(requireContext(), confirmationIntent)
                confirmationLauncher.launch(intentConfirmation)
            }
        }

        viewModel.paymentErrorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                viewModel.clearPaymentErrorMessageLiveData()
                val messageText = errorMessage.getText(requireContext())
                showErrorDialog(messageText)
            }
        }

        viewModel.buyPaymentResultSuccess.observe(viewLifecycleOwner) { result ->
            if (result == true) {
                viewModel.clearSendPaymentsResultSuccess()
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.payment_success_title)
                    .setMessage(R.string.payment_success_message)
                    .setCancelable(false)
                    .setPositiveButton(
                        R.string.payment_success_positive_btn
                    ) { _, _ ->
                        setFragmentResult(
                            FragmentResultUtils.REQUEST_UPDATE_ROUTE_PAID_AFTER_BUY,
                            bundleOf(FragmentResultUtils.BUNDLE_UPDATE_ROUTE_PAID_AFTER_BUY to true)
                        )
                        this.dismiss()
                    }
                    .show()
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
            val tokenizationResult = UkassaHelper.getTokenizationResultFromResult(data)
            viewModel.saveTokenizationResultResult(tokenizationResult)
            viewModel.sendPayment()
        }
    }

    private fun showTokenizeError() {
        Toast.makeText(requireContext(), R.string.tokenization_canceled, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorDialog(errorMessage: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(errorMessage)
            .setPositiveButton(
                R.string.payment_error_positive_btn
            ) { _, _ -> }
            .show()
    }
}