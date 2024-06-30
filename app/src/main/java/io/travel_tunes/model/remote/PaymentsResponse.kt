package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentsResponse(
    @Expose private val id: String,
    @Expose private val status: String,
    @Expose private val paid: Boolean,
    @Expose private val confirmation: ConfirmationData?,
    @Expose private val metadata: MetadataRequest?,
    @SerializedName("cancellation_details") @Expose private val cancellationDetails: CancellationDetails?
) : Parcelable {
    companion object {
        private const val STATUS_SUCCEEDED = "succeeded"
        private const val STATUS_PENDING = "pending"
        private const val STATUS_CANCELED = "canceled"
    }

    fun getId() = id

    fun isPaymentPaidSuccess() = isStatusSucceeded() && paid

    fun getConfirmationData() = confirmation

    fun getMetadataPaymentTag() = metadata?.getPaymentVariant()

    private fun isStatusSucceeded(): Boolean {
        return status.equals(STATUS_SUCCEEDED, true)
    }

    fun isStatusPending(): Boolean {
        return status.equals(STATUS_PENDING, true)
    }

    fun isStatusCanceled(): Boolean {
        return status.equals(STATUS_CANCELED, true)
    }

    fun getCancellationDetails() = cancellationDetails
}