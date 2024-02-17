package io.travel_tunes.model.route

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoutesData(
    private val routes: List<RouteItemInfo>
): Parcelable {
    fun getRoutes() = routes
}