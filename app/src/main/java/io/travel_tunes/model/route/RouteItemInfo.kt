package io.travel_tunes.model.route

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.travel_tunes.utils.map.LatLngNew
import kotlinx.parcelize.Parcelize

@Parcelize
data class RouteItemInfo(
    private val id: String,
    private val title: String,
    private val tag: String,
    private val description: String,
    @SerializedName("description_short") private val descriptionShort: String,
    private val distance: String,
    private val duration: String,
    private val points: List<PointItemInfo>,
    private val northeastLat: String,
    private val northeastLon: String,
    private val southwestLat: String,
    private val southwestLon: String
): Parcelable {
    fun getTitle() = title
    fun getTag() = tag
    fun getDescription() = description
    fun getDescriptionShort() = descriptionShort
    fun getDistance() = distance
    fun getDuration() = duration
    fun getPoints() = points
    fun getNortheast() = LatLngNew(northeastLat.toDoubleOrNull() ?: 0.0, northeastLon.toDoubleOrNull() ?: 0.0)
    fun getSouthwest() = LatLngNew(southwestLat.toDoubleOrNull() ?: 0.0, southwestLon.toDoubleOrNull() ?: 0.0)
}