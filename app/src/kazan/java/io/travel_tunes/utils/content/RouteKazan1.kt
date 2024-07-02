package io.travel_tunes.utils.content

import android.content.Context
import io.travel_tunes.R
import io.travel_tunes.model.route.PointItemFullInfo
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.parseJsonRes
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RouteKazan1(
    private val tag: String = "kazan_1",
    private val jsonDescription: Int = R.raw.route_kazan_1,
    private val routeKml: Int = R.raw.route_kazan_1_kml,
    private val pictureMainRes: Int = R.drawable.route_kazan_1_main,
    //Используем изображение первой точки маршрута в описании маршрута
    private val pictureDescriptionRes: Int = R.drawable.route_kazan_1_1,
    private val audioRes: Int = R.raw.route_kazan_1_description
) : RouteSealedInfo() {
    override fun getRouteTag() = tag.lowercase()

    @IgnoredOnParcel
    private var routeInfo: RouteItemInfo? = null

    override fun getRouteKml() = routeKml

    override fun getRouteMainPictureRes() = pictureMainRes
    override fun getRouteDescriptionPictureRes() = pictureDescriptionRes
    override fun getRouteAudioRes() = audioRes
    override fun getPointPictureResList(): List<Int> {
        return listOf(
            R.drawable.route_kazan_1_1,
            R.drawable.route_kazan_1_2,
            R.drawable.route_kazan_1_3,
            R.drawable.route_kazan_1_4,
            R.drawable.route_kazan_1_5,
            R.drawable.route_kazan_1_6,
            R.drawable.route_kazan_1_7,
            R.drawable.route_kazan_1_8,
            R.drawable.route_kazan_1_9,
            R.drawable.route_kazan_1_10,
            R.drawable.route_kazan_1_11,
            R.drawable.route_kazan_1_12,
            R.drawable.route_kazan_1_13,
            R.drawable.route_kazan_1_14,
            R.drawable.route_kazan_1_15,
            R.drawable.route_kazan_1_16,
            R.drawable.route_kazan_1_17,
            R.drawable.route_kazan_1_18,
            R.drawable.route_kazan_1_19,
            R.drawable.route_kazan_1_20,
            R.drawable.route_kazan_1_21,
            R.drawable.route_kazan_1_22,
            R.drawable.route_kazan_1_23,
            R.drawable.route_kazan_1_24,
            R.drawable.route_kazan_1_25,
            R.drawable.route_kazan_1_26
        )
    }

    override fun getPointAudioResList(): List<Int> {
        return listOf(
            R.raw.route_kazan_1_1,
            R.raw.route_kazan_1_2,
            R.raw.route_kazan_1_3,
            R.raw.route_kazan_1_4,
            R.raw.route_kazan_1_5,
            R.raw.route_kazan_1_6,
            R.raw.route_kazan_1_7,
            R.raw.route_kazan_1_8,
            R.raw.route_kazan_1_9,
            R.raw.route_kazan_1_10,
            R.raw.route_kazan_1_11,
            R.raw.route_kazan_1_12,
            R.raw.route_kazan_1_13,
            R.raw.route_kazan_1_14,
            R.raw.route_kazan_1_15,
            R.raw.route_kazan_1_16,
            R.raw.route_kazan_1_17,
            R.raw.route_kazan_1_18,
            R.raw.route_kazan_1_19,
            R.raw.route_kazan_1_20,
            R.raw.route_kazan_1_21,
            R.raw.route_kazan_1_22,
            R.raw.route_kazan_1_23,
            R.raw.route_kazan_1_24,
            R.raw.route_kazan_1_25,
            R.raw.route_kazan_1_26
        )
    }

    override fun getRouteItemInfo(context: Context): RouteItemInfo {
        routeInfo?.let {
            return it
        } ?: run {
            val result = parseJsonRes(context, jsonDescription)
            routeInfo = result
            return result
        }
    }

    override fun getPointItemFullInfo(id: String): PointItemFullInfo? {
        routeInfo?.let { route ->
            val point = route.getPoints().firstOrNull { id == it.getId() } ?: return null
            val audioRes = getPointAudioResList().getOrNull(id.toInt())
            val drawableRes = getPointPictureResList().getOrNull(id.toInt())
            return PointItemFullInfo(
                pointItemInfo = point,
                audioRes = audioRes,
                pictureRes = drawableRes
            )
        } ?: run { return null }
    }
}