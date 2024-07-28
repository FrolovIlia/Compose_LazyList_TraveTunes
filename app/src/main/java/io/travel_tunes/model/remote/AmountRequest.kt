package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AmountRequest(
    @Expose @SerializedName("currency") private val currency: String,
    @Expose @SerializedName("value") private val value: String,
) : Parcelable