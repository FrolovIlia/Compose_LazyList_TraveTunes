package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CancellationDetails(
    @Expose @SerializedName("party") private val party: String,
    @Expose @SerializedName("reason") private val reason: String
) : Parcelable {
    fun getReason() = reason
}