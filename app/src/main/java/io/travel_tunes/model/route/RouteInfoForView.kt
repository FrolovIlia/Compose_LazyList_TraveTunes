package io.travel_tunes.model.route

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import io.travel_tunes.utils.map.LatLngNew
import kotlinx.parcelize.Parcelize

/**
 * класс для отказа от использования context
 * альтернатива RouteSealedInfo
 */
@Parcelize
data class RouteInfoForView(
    private val isPaid: Boolean,
    private val freePointsCount: Int,
    private val routeTag: String,
    private val routeMapPoints: List<LatLngNew>,
    @DrawableRes private val routePictureMainRes: Int,
    @DrawableRes private val routePictureDescriptionRes: Int,
    @RawRes private val routeAudioRes: Int?,
    @DrawableRes private val routePointsPicturesResList: List<Int>,
    @RawRes private val routePointsAudiosResList: List<Int>,
    private val routeItemInfo: RouteItemInfo
) : Parcelable {
    fun isPaid() = isPaid
    fun getFreePointsCount() = freePointsCount
    fun getRouteTag() = routeTag
    fun getRouteMapPoints() = routeMapPoints
    fun getRoutePictureMainRes() = routePictureMainRes
    fun getRoutePictureDescriptionRes() = routePictureDescriptionRes
    fun getRouteAudioRes() = routeAudioRes
    fun getRoutePointsPicturesResList() = routePointsPicturesResList
    fun getRoutePointsAudiosResList() = routePointsAudiosResList
    fun getRouteItemInfo() = routeItemInfo

    fun getPointItemFullInfo(id: String): PointItemFullInfo? {
        val idForResources = id.toInt()-1
        val point = routeItemInfo.getPoints().firstOrNull { id == it.getId() } ?: return null
        val audioRes = getRoutePointsAudiosResList().getOrNull(idForResources)
        val drawableRes = getRoutePointsPicturesResList().getOrNull(idForResources)
        return PointItemFullInfo(
            pointItemInfo = point,
            audioRes = audioRes,
            pictureRes = drawableRes
        )
    }
}