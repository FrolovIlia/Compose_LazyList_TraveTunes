package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentsRequest(
    @Expose private val amount: AmountRequest,
    @Expose private val capture: Boolean, //???
//    @Expose private val description: String, //???
    @Expose private val metadata: MetadataRequest,
    @Expose @SerializedName("payment_token") private val paymentToken: Boolean,
): Parcelable