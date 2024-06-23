package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetadataRequest(
    @Expose @SerializedName("device_unique_id") private val uniqueId: String,
    @Expose @SerializedName("item_payment_variant") private val paymentVariant: String
): Parcelable