package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class FailureDetails(
    @Expose private val type: String,
    @Expose private val id: String,
    @Expose private val code: String,
    @Expose private val description: String?,
    @Expose private val parameter: String?
): Parcelable {
    fun getCode() = code
}