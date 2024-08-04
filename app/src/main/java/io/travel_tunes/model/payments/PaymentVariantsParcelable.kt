package io.travel_tunes.model.payments

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentVariantsParcelable(
    @SerializedName("paymentVariants") private val paymentVariants: List<PaymentVariant>
) : Parcelable {
    fun getPaymentVariants() = paymentVariants
}