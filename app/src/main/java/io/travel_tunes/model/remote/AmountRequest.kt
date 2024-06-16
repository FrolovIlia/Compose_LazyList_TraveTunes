package io.travel_tunes.model.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class AmountRequest(
    @Expose private val currency: String,
    @Expose private val value: String,
): Parcelable