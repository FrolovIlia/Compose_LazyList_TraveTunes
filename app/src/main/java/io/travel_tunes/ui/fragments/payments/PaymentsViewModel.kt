package io.travel_tunes.ui.fragments.payments

import androidx.lifecycle.MutableLiveData
import io.travel_tunes.data.remote.Resource
import io.travel_tunes.data.repository.DefaultRepository
import io.travel_tunes.data.repository.PaymentsRepository
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.model.remote.CancellationDetails
import io.travel_tunes.utils.CrashlyticsUtils
import io.travel_tunes.utils.MessageProgress
import io.travel_tunes.utils.base.BaseViewModel
import io.travel_tunes.utils.extencions.SingleLiveEvent
import io.travel_tunes.utils.payment.ConfirmationIntentData
import io.travel_tunes.utils.payment.UkassaHelper
import kotlinx.coroutines.async
import ru.yoomoney.sdk.kassa.payments.TokenizationResult

class PaymentsViewModel(
    paymentVariants: List<PaymentVariant>,
    private val paymentsRepository: PaymentsRepository,
    private val defaultRepository: DefaultRepository
) : BaseViewModel() {

    private var selectedPaymentVariant: PaymentVariant? = null
    private var tokenizationResult: TokenizationResult? = null
    private var tempConfirmationPaymentId: String? = null

    val paymentVariantsLiveData = MutableLiveData(paymentVariants)

    private val _buyPaymentResultSuccess = SingleLiveEvent<Boolean?>()
    val buyPaymentResultSuccess
        get() = _buyPaymentResultSuccess

    private val _paymentsConfirmationIntent = SingleLiveEvent<ConfirmationIntentData?>()
    val paymentsConfirmationIntent
        get() = _paymentsConfirmationIntent

    private val _paymentErrorMessageLiveData = SingleLiveEvent<MessageProgress?>()
    val paymentErrorMessageLiveData
        get() = _paymentErrorMessageLiveData

    fun handleOnPaymentsAdapterClick(paymentVariant: PaymentVariant) {
        selectedPaymentVariant = paymentVariant
    }

    fun saveTokenizationResultResult(tokenizationResult: TokenizationResult) {
        this.tokenizationResult = tokenizationResult
    }

    fun sendPayment() {
        val paymentToken = tokenizationResult?.paymentToken ?: return
        if (selectedPaymentVariant == null || paymentToken.isBlank()) return
        showProgressDialog()
        launchAtViewModelScope {
            val deferred = async {
                paymentsRepository.sendPayments(
                    selectedPaymentVariant!!,
                    paymentToken
                )
            }
            when (val result = deferred.await()) {
                is Resource.Success -> {
                    val response = result.value
                    when {
                        response.isPaymentPaidSuccess() -> {
                            actionAfterSuccessBuy()
                        }

                        response.isStatusPending() -> {
                            // 3ds confirmation
                            val confirmationData = response.getConfirmationData()
                            if (confirmationData?.isRedirectType() == true && tokenizationResult?.paymentMethodType != null) {
                                // стартуем редирект
                                val confirmationIntentData = ConfirmationIntentData(
                                    confirmationUrl = confirmationData.getConfirmationUrl(),
                                    paymentMethodType = tokenizationResult?.paymentMethodType!!
                                )
                                tempConfirmationPaymentId = response.getId()
                                if (_paymentsConfirmationIntent.value != confirmationIntentData) {
                                    _paymentsConfirmationIntent.postValue(confirmationIntentData)
                                }
                            } else {
                                CrashlyticsUtils.sendThrowableNonFatal(Throwable("sendPayment success response = $response"))
                            }
                        }

                        response.isStatusCanceled() -> {
                            showPaymentCancelledError(response.getCancellationDetails())
                        }
                    }
                }

                is Resource.Failure -> {
                    handleFailureErrors(result)
                }

            }
            clearProgressDialog()
        }
    }

    private fun loadPaymentById() {
        val paymentId = tempConfirmationPaymentId
        if (paymentId.isNullOrBlank()) return
        showProgressDialog()
        launchAtViewModelScope {
            val deferred = async {
                paymentsRepository.getPaymentById(paymentId)
            }
            val result = deferred.await()
            tempConfirmationPaymentId = null
            when (result) {
                is Resource.Success -> {
                    val response = result.value
                    when {
                        response.isStatusCanceled() -> {
                            showPaymentCancelledError(response.getCancellationDetails())
                        }

                        response.isPaymentPaidSuccess() -> {
                            actionAfterSuccessBuy()
                        }

                        else -> {
                            // TODO: any another success variants?
                            CrashlyticsUtils.sendThrowableNonFatal(
                                Throwable(
                                    "loadPaymentById success notPaid|pending response = $response"
                                )
                            )
                        }
                    }
                }

                is Resource.Failure -> {
                    handleFailureErrors(result)
                }
            }
            clearProgressDialog()
        }
    }

    fun clearSendPaymentsResultSuccess() {
        _buyPaymentResultSuccess.call()
    }

    fun clearPaymentsConfirmationIntent() {
        _paymentsConfirmationIntent.call()
    }

    fun clearPaymentErrorMessageLiveData() {
        _paymentErrorMessageLiveData.call()
    }

    fun handleSuccessConfirmation() {
        loadPaymentById()
    }

    private suspend fun actionAfterSuccessBuy() {
        // показать
        defaultRepository.updateRouteInfoAfterSuccessBuy()
        if (_buyPaymentResultSuccess.value != true) {
            _buyPaymentResultSuccess.postValue(true)
        }
    }

    private fun showPaymentCancelledError(cancellationDetails: CancellationDetails?) {
        val reason = cancellationDetails?.getReason() ?: return
        if (reason.isBlank()) return
        val errorMessage = UkassaHelper.getMessageForCancelReason(reason)
        if (_paymentErrorMessageLiveData.value != errorMessage) {
            _paymentErrorMessageLiveData.postValue(errorMessage)
        }
    }

    private fun handleFailureErrors(failure: Resource.Failure) {
        val errorMessage = UkassaHelper.getMessageForFailureError(failure)
        if (_paymentErrorMessageLiveData.value != errorMessage) {
            _paymentErrorMessageLiveData.postValue(errorMessage)
        }
    }
}