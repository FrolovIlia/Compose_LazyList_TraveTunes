package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentsResponsesList(
    @Expose @SerializedName("id") private val id: String,
    @Expose @SerializedName("status") private val status: String,
    @Expose @SerializedName("paid") private val paid: Boolean
) : Parcelable