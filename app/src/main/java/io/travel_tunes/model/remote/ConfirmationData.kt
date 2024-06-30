package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConfirmationData(
    @Expose private val type: String, // redirect
    @Expose @SerializedName("confirmation_url") private val confirmationUrl: String
): Parcelable {
    fun getConfirmationUrl() = confirmationUrl
    fun isRedirectType() = type.equals("redirect", true)
}