package io.travel_tunes.utils.content

import android.content.Context
import io.travel_tunes.R
import io.travel_tunes.model.route.PointItemFullInfo
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.parseJsonRes
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RouteIvanovo2(
    private val tag: String = "ivanovo_2",
    private val jsonDescription: Int = R.raw.route_ivanovo_2,
    private val pictureRes: Int = R.drawable.pic_ivanovo_city,
    private val audioRes: Int = R.raw.route_ivanovo_1_description
) : RouteSealedInfo() {

    @IgnoredOnParcel
    private var routeInfo: RouteItemInfo? = null

    override fun getRoutePictureRes() = pictureRes
    override fun getRouteAudioRes() = audioRes
    override fun getPointPictureResList(): List<Int> {
        return listOf(
            R.drawable.route_ivanovo_1_1,
            R.drawable.route_ivanovo_1_2,
            R.drawable.route_ivanovo_1_3,
            R.drawable.route_ivanovo_1_4,
            R.drawable.route_ivanovo_1_5,
            R.drawable.route_ivanovo_1_6,
            R.drawable.route_ivanovo_1_7,
            R.drawable.route_ivanovo_1_8,
            R.drawable.route_ivanovo_1_9,
            R.drawable.route_ivanovo_1_10,
            R.drawable.route_ivanovo_1_11,
            R.drawable.route_ivanovo_1_12,
            R.drawable.route_ivanovo_1_13,
            R.drawable.route_ivanovo_1_14,
            R.drawable.route_ivanovo_1_15,
            R.drawable.route_ivanovo_1_16,
            R.drawable.route_ivanovo_1_17,
            R.drawable.route_ivanovo_1_18,
            R.drawable.route_ivanovo_1_19,
            R.drawable.route_ivanovo_1_20,
            R.drawable.route_ivanovo_1_21,
            R.drawable.route_ivanovo_1_22,
            R.drawable.route_ivanovo_1_23,
            R.drawable.route_ivanovo_1_24,
            R.drawable.route_ivanovo_1_25,
            R.drawable.route_ivanovo_1_26,
            R.drawable.route_ivanovo_1_27,
            R.drawable.route_ivanovo_1_28,
            R.drawable.route_ivanovo_1_29,
            R.drawable.route_ivanovo_1_30,
            R.drawable.route_ivanovo_1_31,
            R.drawable.route_ivanovo_1_32
        )
    }

    override fun getPointAudioResList(): List<Int> {
        return listOf(
            R.raw.route_ivanovo_1_1,
            R.raw.route_ivanovo_1_2,
            R.raw.route_ivanovo_1_3,
            R.raw.route_ivanovo_1_4,
            R.raw.route_ivanovo_1_5,
            R.raw.route_ivanovo_1_6,
            R.raw.route_ivanovo_1_7,
            R.raw.route_ivanovo_1_8,
            R.raw.route_ivanovo_1_9,
            R.raw.route_ivanovo_1_10,
            R.raw.route_ivanovo_1_11,
            R.raw.route_ivanovo_1_12,
            R.raw.route_ivanovo_1_13,
            R.raw.route_ivanovo_1_14,
            R.raw.route_ivanovo_1_15,
            R.raw.route_ivanovo_1_16,
            R.raw.route_ivanovo_1_17,
            R.raw.route_ivanovo_1_18,
            R.raw.route_ivanovo_1_19,
            R.raw.route_ivanovo_1_20,
            R.raw.route_ivanovo_1_21,
            R.raw.route_ivanovo_1_22,
            R.raw.route_ivanovo_1_23,
            R.raw.route_ivanovo_1_24,
            R.raw.route_ivanovo_1_25,
            R.raw.route_ivanovo_1_26,
            R.raw.route_ivanovo_1_27,
            R.raw.route_ivanovo_1_28,
            R.raw.route_ivanovo_1_29,
            R.raw.route_ivanovo_1_30,
            R.raw.route_ivanovo_1_31,
            R.raw.route_ivanovo_1_32
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
            return PointItemFullInfo(
                pointItemInfo = point,
                audioRes = audioRes,
                pictureRes = pictureRes
            )
        } ?: run { return null }
    }

}