package io.travel_tunes.model.route

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.travel_tunes.utils.map.LatLngNew
import kotlinx.parcelize.Parcelize

@Parcelize
data class RouteItemInfo(
    @SerializedName("title") private val title: String,
    @SerializedName("description") private val description: String,
    @SerializedName("description_short") private val descriptionShort: String,
    @SerializedName("distance") private val distance: String,
    @SerializedName("duration") private val duration: String,
    @SerializedName("points") private val points: List<PointItemInfo>,
    @SerializedName("northeastLat") private val northeastLat: String,
    @SerializedName("northeastLon") private val northeastLon: String,
    @SerializedName("southwestLat") private val southwestLat: String,
    @SerializedName("southwestLon") private val southwestLon: String
) : Parcelable {
    fun getTitle() = title
    fun getDescription() = description
    fun getDescriptionShort() = descriptionShort
    fun getDistance() = distance
    fun getDuration() = duration
    fun getPoints() = points
    fun getPoints(isPaid: Boolean) =
        if (isPaid) points.map { pointItemInfo -> pointItemInfo.copy(isEnabled = true) } else points.mapIndexed { index, pointItemInfo ->
            pointItemInfo.copy(isEnabled = index < 5)
        }

    fun getNortheast() =
        LatLngNew(northeastLat.toDoubleOrNull() ?: 0.0, northeastLon.toDoubleOrNull() ?: 0.0)

    fun getSouthwest() =
        LatLngNew(southwestLat.toDoubleOrNull() ?: 0.0, southwestLon.toDoubleOrNull() ?: 0.0)
}