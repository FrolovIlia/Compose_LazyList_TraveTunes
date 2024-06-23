package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentsResponse(
    @Expose private val id: String,
    @Expose private val status: String,
    @Expose private val paid: Boolean
): Parcelable {
    fun isStatusPending(): Boolean {
        return status.equals("pending", true)
    }
    fun isStatusSucceeded(): Boolean {
        return status.equals("succeeded", true)
    }
    fun isStatusCanceled(): Boolean {
        return status.equals("canceled", true)
    }
}