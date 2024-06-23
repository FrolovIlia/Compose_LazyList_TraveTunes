package io.travel_tunes.ui.fragments.payments

import androidx.lifecycle.MutableLiveData
import io.travel_tunes.data.remote.Resource
import io.travel_tunes.data.repository.PaymentsRepository
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.model.remote.PaymentsResponse
import io.travel_tunes.utils.base.BaseViewModel
import io.travel_tunes.utils.extencions.SingleLiveEvent
import kotlinx.coroutines.async

class PaymentsViewModel(
    paymentVariants: List<PaymentVariant>,
    private val paymentsRepository: PaymentsRepository
) : BaseViewModel() {

    private var selectedPaymentVariant: PaymentVariant? = null
    private var tempTokenValue: String? = null

    val paymentVariantsLiveData = MutableLiveData(paymentVariants)

    private val _sendPaymentsResult = SingleLiveEvent<Resource<PaymentsResponse>?>()
    val sendPaymentsResult
        get() = _sendPaymentsResult

    fun handleOnPaymentsAdapterClick(paymentVariant: PaymentVariant) {
        selectedPaymentVariant = paymentVariant
    }

    fun saveTokenResult(token: String) {
        tempTokenValue = token
    }

    fun sendPayment() {
        if (selectedPaymentVariant == null || tempTokenValue.isNullOrBlank()) return
        showProgressDialog()
        launchAtViewModelScope {
            val deferred = async {
                paymentsRepository.sendPayments(
                    selectedPaymentVariant!!,
                    tempTokenValue!!
                )
            }
            val result = deferred.await()
            if (_sendPaymentsResult.value != result) {
                _sendPaymentsResult.postValue(result)
            }
//            if (result.isSuccess()) {
//                (result as Resource.Success).let {
//                    /**
//                     * показать окно с результатом покупки
//                     * закрыть текущее окно покупки
//                     * обновить локальную инфу о покупках
//                     */
//                }
//            } else {
//                /**
//                 * показать ошибки юзеру
//                 */
//            }
            clearProgressDialog()
        }
    }
}