package io.travel_tunes.ui.fragments.payments

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.travel_tunes.data.repository.DefaultRepository
import io.travel_tunes.data.repository.PaymentsRepository
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.utils.base.BaseViewModelFactory

class PaymentsViewModelFactory @AssistedInject constructor(
    @Assisted(tag) private val paymentVariants: List<PaymentVariant>,
    private val paymentsRepository: PaymentsRepository,
    private val defaultRepository: DefaultRepository
) : BaseViewModelFactory<PaymentsViewModel>() {

    companion object {
        private const val tag = "payment_variants"
    }
    override fun getViewModel(): PaymentsViewModel {
        return PaymentsViewModel(paymentVariants, paymentsRepository, defaultRepository)
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(tag) paymentVariants: List<PaymentVariant>): PaymentsViewModelFactory
    }
}