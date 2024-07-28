package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FailureDetails(
    @Expose @SerializedName("type") private val type: String,
    @Expose @SerializedName("id") private val id: String,
    @Expose @SerializedName("code") private val code: String,
    @Expose @SerializedName("description") private val description: String?,
    @Expose @SerializedName("parameter") private val parameter: String?
) : Parcelable {
    fun getCode() = code
}