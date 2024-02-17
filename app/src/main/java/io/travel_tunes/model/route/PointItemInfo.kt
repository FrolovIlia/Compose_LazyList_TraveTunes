package io.travel_tunes.model.route

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointItemInfo(
    private val id: String,
    private val title: String,
    private val description: String,
    private val locationLat: String,
    private val locationLon: String
): Parcelable