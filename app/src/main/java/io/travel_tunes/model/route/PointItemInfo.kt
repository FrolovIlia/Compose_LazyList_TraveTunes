package io.travel_tunes.model.route

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointItemInfo(
    private val id: String,
    private val title: String,
    private val description: String,
    private val locationLat: String,
    private val locationLon: String
): Parcelable {

    fun getTitle() = title
    fun getPosition() = LatLng(locationLat.toDouble(), locationLon.toDouble())
}