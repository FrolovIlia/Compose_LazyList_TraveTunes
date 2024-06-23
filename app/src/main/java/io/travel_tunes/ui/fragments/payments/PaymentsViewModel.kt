package io.travel_tunes.ui.fragments.payments

import androidx.lifecycle.MutableLiveData
import io.travel_tunes.data.repository.PaymentsRepository
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.utils.base.BaseViewModel

class PaymentsViewModel(
    paymentVariants: List<PaymentVariant>,
    private val paymentsRepository: PaymentsRepository
) : BaseViewModel() {

    private var selectedPaymentVariant: PaymentVariant? = null
    private var tempTokenValue: String? = null

    val paymentVariantsLiveData = MutableLiveData(paymentVariants)

    fun handleOnPaymentsAdapterClick(paymentVariant: PaymentVariant) {
        selectedPaymentVariant = paymentVariant
    }

    fun saveTokenResult(token: String) {
        tempTokenValue = token
    }

    fun sendPayment() {
        if (selectedPaymentVariant == null || tempTokenValue.isNullOrBlank()) return
        launchAtViewModelScope {
            paymentsRepository.sendPayments(selectedPaymentVariant!!, tempTokenValue!!)
        }
    }
}