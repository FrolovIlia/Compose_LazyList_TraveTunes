package io.travel_tunes.utils.content

import android.content.Context
import io.travel_tunes.R
import io.travel_tunes.model.route.PointItemFullInfo
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.map.LatLngNew
import io.travel_tunes.utils.parseJsonRes
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RouteSuzdal2(
    private val tag: String = "suzdal_2",
    private val jsonDescription: Int = R.raw.route_suzdal_2,
    //Используем общее изображение, но в данном случае первая была очень невзрачная, так что выбрана что симпотичнее
    private val pictureMainRes: Int = R.drawable.route_suzdal_2_1,
    //Используем изображение первой точки маршрута в описании маршрута, но в данном случае первая была очень невзрачная, так что выбрана что симпотичнее
    private val pictureDescriptionRes: Int = R.drawable.route_suzdal_2_1,
    private val audioRes: Int = R.raw.route_suzdal_2_description
) : RouteSealedInfo() {
    override fun getRouteTag() = tag.lowercase()

    @IgnoredOnParcel
    private var routeInfo: RouteItemInfo? = null


    override fun getRouteMainPictureRes() = pictureMainRes
    override fun getRouteDescriptionPictureRes() = pictureDescriptionRes
    override fun getRouteAudioRes() = audioRes
    override fun getPointPictureResList(): List<Int> {
        return listOf(
            R.drawable.route_suzdal_2_1,
            R.drawable.route_suzdal_2_2,
            R.drawable.route_suzdal_2_3,
            R.drawable.route_suzdal_2_4,
            R.drawable.route_suzdal_2_5,
            R.drawable.route_suzdal_2_6,
            R.drawable.route_suzdal_2_7,
            R.drawable.route_suzdal_2_8,
            R.drawable.route_suzdal_2_9,
            R.drawable.route_suzdal_2_10,
            R.drawable.route_suzdal_2_11,
            R.drawable.route_suzdal_2_12,
            R.drawable.route_suzdal_2_13,
            R.drawable.route_suzdal_2_14,
            R.drawable.route_suzdal_2_15,
            R.drawable.route_suzdal_2_16,
            R.drawable.route_suzdal_2_17,
            R.drawable.route_suzdal_2_18,
            R.drawable.route_suzdal_2_19,
            R.drawable.route_suzdal_2_20,
            R.drawable.route_suzdal_2_21,
            R.drawable.route_suzdal_2_22,
            R.drawable.route_suzdal_2_23,
            R.drawable.route_suzdal_2_24,
            R.drawable.route_suzdal_2_25,
            R.drawable.route_suzdal_2_26
        )
    }

    override fun getPointAudioResList(): List<Int> {
        return listOf(
            R.raw.route_suzdal_2_1,
            R.raw.route_suzdal_2_2,
            R.raw.route_suzdal_2_3,
            R.raw.route_suzdal_2_4,
            R.raw.route_suzdal_2_5,
            R.raw.route_suzdal_2_6,
            R.raw.route_suzdal_2_7,
            R.raw.route_suzdal_2_8,
            R.raw.route_suzdal_2_9,
            R.raw.route_suzdal_2_10,
            R.raw.route_suzdal_2_11,
            R.raw.route_suzdal_2_12,
            R.raw.route_suzdal_2_13,
            R.raw.route_suzdal_2_14,
            R.raw.route_suzdal_2_15,
            R.raw.route_suzdal_2_16,
            R.raw.route_suzdal_2_17,
            R.raw.route_suzdal_2_18,
            R.raw.route_suzdal_2_19,
            R.raw.route_suzdal_2_20,
            R.raw.route_suzdal_2_21,
            R.raw.route_suzdal_2_22,
            R.raw.route_suzdal_2_23,
            R.raw.route_suzdal_2_24,
            R.raw.route_suzdal_2_25,
            R.raw.route_suzdal_2_26
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

    override fun getRoutePolygon(): List<LatLngNew> {
        return listOf(

            LatLngNew(56.421606, 40.45056),
            LatLngNew(56.421141, 40.450056),
            LatLngNew(56.420894, 40.449997),
            LatLngNew(56.420764, 40.449208),
            LatLngNew(56.420046, 40.449578),
            LatLngNew(56.419986, 40.449133),
            LatLngNew(56.420206, 40.449026),
            LatLngNew(56.420179, 40.448291),
            LatLngNew(56.421084, 40.448178),
            LatLngNew(56.421166, 40.447355),
            LatLngNew(56.42127, 40.447108),
            LatLngNew(56.421554, 40.447178),
            LatLngNew(56.421961, 40.44729),
            LatLngNew(56.422805, 40.447116),
            LatLngNew(56.423437, 40.446966),
            LatLngNew(56.423565, 40.446966),
            LatLngNew(56.423643, 40.446893),
            LatLngNew(56.423726, 40.446518),
            LatLngNew(56.424186, 40.446335),
            LatLngNew(56.425987, 40.445912),
            LatLngNew(56.425838, 40.442076),
            LatLngNew(56.426313, 40.441851),
            LatLngNew(56.426657, 40.441518),
            LatLngNew(56.427304, 40.441636),
            LatLngNew(56.427304, 40.440864),
            LatLngNew(56.427299, 40.440636),
            LatLngNew(56.427357, 40.440477),
            LatLngNew(56.427612, 40.44051),
            LatLngNew(56.428028, 40.440671),
            LatLngNew(56.428781, 40.441025),
            LatLngNew(56.428835, 40.441129),
            LatLngNew(56.428866, 40.441266),
            LatLngNew(56.428912, 40.44155),
            LatLngNew(56.429042, 40.442677),
            LatLngNew(56.429017, 40.44324),
            LatLngNew(56.42903, 40.443608),
            LatLngNew(56.429125, 40.444018),
            LatLngNew(56.429223, 40.444581),
            LatLngNew(56.432186, 40.443085),
            LatLngNew(56.431955, 40.442065),
            LatLngNew(56.432442, 40.44089),
            LatLngNew(56.433628, 40.440376),
            LatLngNew(56.433382, 40.438825),
            LatLngNew(56.432999, 40.439211),
            LatLngNew(56.432971, 40.439346),
            LatLngNew(56.433059, 40.43959),
            LatLngNew(56.432738, 40.440099),
            LatLngNew(56.432359, 40.440936),
            LatLngNew(56.431926, 40.441961),
            LatLngNew(56.431857, 40.441644),
            LatLngNew(56.431745, 40.440764),
            LatLngNew(56.431715, 40.440083),
            LatLngNew(56.431958, 40.439815),
            LatLngNew(56.43216, 40.439514),
            LatLngNew(56.432353, 40.439075),
            LatLngNew(56.432462, 40.438855),
            LatLngNew(56.432534, 40.438688),
            LatLngNew(56.432531, 40.438431),
            LatLngNew(56.432477, 40.437948),
            LatLngNew(56.432415, 40.437728),
            LatLngNew(56.43232, 40.437573),
            LatLngNew(56.432077, 40.437353),
            LatLngNew(56.431994, 40.437047),
            LatLngNew(56.430872, 40.436666),
            LatLngNew(56.430254, 40.436314),
            LatLngNew(56.428401, 40.43535),
            LatLngNew(56.428288, 40.436273),
            LatLngNew(56.428602, 40.436256),
            LatLngNew(56.429117, 40.436597),
            LatLngNew(56.429342, 40.436813),
            LatLngNew(56.429648, 40.436907),
            LatLngNew(56.429794, 40.43691),
            LatLngNew(56.429893, 40.436937),
            LatLngNew(56.429983, 40.437009),
            LatLngNew(56.429977, 40.437098),
            LatLngNew(56.429891, 40.437412),
            LatLngNew(56.429817, 40.437747),
            LatLngNew(56.429742, 40.437836),
            LatLngNew(56.429633, 40.437846),
            LatLngNew(56.429488, 40.437771),
            LatLngNew(56.429206, 40.437605),
            LatLngNew(56.429168, 40.437645),
            LatLngNew(56.429128, 40.437857),
            LatLngNew(56.428757, 40.437554),
            LatLngNew(56.428702, 40.437602),
            LatLngNew(56.428606, 40.437916),
            LatLngNew(56.428522, 40.438018),
            LatLngNew(56.428457, 40.437903),
            LatLngNew(56.428143, 40.437331)
        )
    }
}