package io.travel_tunes.model.route

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
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
    private val points: List<PointItemInfo>
): Parcelable {
    fun getTitle() = title
    fun getTag() = tag
    fun getDescription() = description
    fun getDescriptionShort() = descriptionShort
    fun getDistance() = distance
    fun getDuration() = duration
    fun getPoints() = points
}