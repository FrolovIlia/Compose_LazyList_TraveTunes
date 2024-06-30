package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class CancellationDetails(
    @Expose private val party: String,
    @Expose private val reason: String
): Parcelable {
    fun getReason() = reason
}