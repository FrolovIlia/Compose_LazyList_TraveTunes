package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentsResponsesList(
    @Expose private val id: String,
    @Expose private val status: String,
    @Expose private val paid: Boolean
): Parcelable {

}