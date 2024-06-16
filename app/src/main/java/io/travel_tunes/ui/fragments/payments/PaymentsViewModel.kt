package io.travel_tunes.ui.fragments.payments

import androidx.lifecycle.MutableLiveData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.utils.base.BaseViewModel
import io.travel_tunes.utils.base.BaseViewModelFactory

class PaymentsViewModel(
    paymentVariants: List<PaymentVariant>,
) : BaseViewModel() {

    val paymentVariantsLiveData = MutableLiveData(paymentVariants)
}

class PaymentsViewModelFactory @AssistedInject constructor(
    @Assisted(tag) private val paymentVariants: List<PaymentVariant>
) : BaseViewModelFactory<PaymentsViewModel>() {

    companion object {
        private const val tag = "payment_variants"
    }
    override fun getViewModel(): PaymentsViewModel {
        return PaymentsViewModel(paymentVariants)
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(tag) paymentVariants: List<PaymentVariant>): PaymentsViewModelFactory
    }
}