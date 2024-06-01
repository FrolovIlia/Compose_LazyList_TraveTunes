package io.travel_tunes.ui.fragments.payments

import androidx.lifecycle.MutableLiveData
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.utils.base.BaseViewModel
import io.travel_tunes.utils.base.BaseViewModelFactory
import io.travel_tunes.utils.prefs.PreferenceManager

class PaymentsViewModel(
    private val paymentVariants: List<PaymentVariant>,
    private val preferenceManager: PreferenceManager
) : BaseViewModel() {

    val paymentVariantsLiveData = MutableLiveData(paymentVariants)
}

class PaymentsViewModelFactory(
    private val paymentVariants: List<PaymentVariant>,
    private val preferenceManager: PreferenceManager
) : BaseViewModelFactory<PaymentsViewModel>() {
    override fun getViewModel(): PaymentsViewModel {
        return PaymentsViewModel(paymentVariants, preferenceManager)
    }
}