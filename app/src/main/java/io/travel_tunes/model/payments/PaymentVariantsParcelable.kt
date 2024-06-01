package io.travel_tunes.model.payments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentVariantsParcelable(
    private val paymentVariants: List<PaymentVariant>
): Parcelable {
    fun getPaymentVariants() = paymentVariants
}