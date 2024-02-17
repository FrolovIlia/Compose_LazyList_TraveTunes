package io.travel_tunes.model.route

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RouteItemInfo(
    private val id: String,
    private val title: String,
    private val description: String,
    private val distance: String,
    private val duration: String,
    private val points: List<PointItemInfo>
//    private val pictures: List
): Parcelable {
    fun getId() = id
    fun getTitle() = title
    fun getDescription() = description
    fun getDistance() = distance
    fun getDuration() = duration
    fun getPoints() = points
}