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
data class RouteSuzdal1(
    private val tag: String = "suzdal_1",
    private val jsonDescription: Int = R.raw.route_suzdal_1,
    private val pictureMainRes: Int = R.drawable.route_suzdal_1_main,
    //Используем изображение первой точки маршрута в описании маршрута
    private val pictureDescriptionRes: Int = R.drawable.route_suzdal_1_1,
    private val audioRes: Int = R.raw.route_suzdal_1_description
) : RouteSealedInfo() {
    override fun getRouteTag() = tag.lowercase()

    @IgnoredOnParcel
    private var routeInfo: RouteItemInfo? = null


    override fun getRouteMainPictureRes() = pictureMainRes
    override fun getRouteDescriptionPictureRes() = pictureDescriptionRes
    override fun getRouteAudioRes() = audioRes
    override fun getPointPictureResList(): List<Int> {
        return listOf(
            R.drawable.route_suzdal_1_1,
            R.drawable.route_suzdal_1_2,
            R.drawable.route_suzdal_1_3,
            R.drawable.route_suzdal_1_4,
            R.drawable.route_suzdal_1_5,
            R.drawable.route_suzdal_1_6,
            R.drawable.route_suzdal_1_7,
            R.drawable.route_suzdal_1_8,
            R.drawable.route_suzdal_1_9,
            R.drawable.route_suzdal_1_10,
            R.drawable.route_suzdal_1_11,
            R.drawable.route_suzdal_1_12,
            R.drawable.route_suzdal_1_13,
            R.drawable.route_suzdal_1_14,
            R.drawable.route_suzdal_1_15,
            R.drawable.route_suzdal_1_16,
            R.drawable.route_suzdal_1_17,
            R.drawable.route_suzdal_1_18,
            R.drawable.route_suzdal_1_19,
            R.drawable.route_suzdal_1_20,
            R.drawable.route_suzdal_1_21,
            R.drawable.route_suzdal_1_22,
            R.drawable.route_suzdal_1_23,
            R.drawable.route_suzdal_1_24,
            R.drawable.route_suzdal_1_25,
            R.drawable.route_suzdal_1_26
        )
    }

    override fun getPointAudioResList(): List<Int> {
        return listOf(
            R.raw.route_suzdal_1_1,
            R.raw.route_suzdal_1_2,
            R.raw.route_suzdal_1_3,
            R.raw.route_suzdal_1_4,
            R.raw.route_suzdal_1_5,
            R.raw.route_suzdal_1_6,
            R.raw.route_suzdal_1_7,
            R.raw.route_suzdal_1_8,
            R.raw.route_suzdal_1_9,
            R.raw.route_suzdal_1_10,
            R.raw.route_suzdal_1_11,
            R.raw.route_suzdal_1_12,
            R.raw.route_suzdal_1_13,
            R.raw.route_suzdal_1_14,
            R.raw.route_suzdal_1_15,
            R.raw.route_suzdal_1_16,
            R.raw.route_suzdal_1_17,
            R.raw.route_suzdal_1_18,
            R.raw.route_suzdal_1_19,
            R.raw.route_suzdal_1_20,
            R.raw.route_suzdal_1_21,
            R.raw.route_suzdal_1_22,
            R.raw.route_suzdal_1_23,
            R.raw.route_suzdal_1_24,
            R.raw.route_suzdal_1_25,
            R.raw.route_suzdal_1_26
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
            LatLngNew(56.419261, 40.462786),
            LatLngNew(56.419765, 40.462668),
            LatLngNew(56.419753, 40.461853),
            LatLngNew(56.419771, 40.460774),
            LatLngNew(56.419912, 40.45583),
            LatLngNew(56.41993, 40.452912),
            LatLngNew(56.419874, 40.450801),
            LatLngNew(56.41992, 40.449468),
            LatLngNew(56.419935, 40.449259),
            LatLngNew(56.419044, 40.449066),
            LatLngNew(56.418976, 40.448851),
            LatLngNew(56.418929, 40.448416),
            LatLngNew(56.41889, 40.447418),
            LatLngNew(56.418917, 40.447091),
            LatLngNew(56.418712, 40.446956),
            LatLngNew(56.418596, 40.446662),
            LatLngNew(56.418306, 40.446806),
            LatLngNew(56.417538, 40.444969),
            LatLngNew(56.416885, 40.443509),
            LatLngNew(56.416731, 40.44344),
            LatLngNew(56.416621, 40.443365),
            LatLngNew(56.416502, 40.443102),
            LatLngNew(56.416461, 40.442742),
            LatLngNew(56.416478, 40.442571),
            LatLngNew(56.416588, 40.44241),
            LatLngNew(56.416268, 40.441412),
            LatLngNew(56.416339, 40.440822),
            LatLngNew(56.416734, 40.440607),
            LatLngNew(56.416965, 40.440264),
            LatLngNew(56.417182, 40.439808),
            LatLngNew(56.417345, 40.439556),
            LatLngNew(56.417315, 40.439486),
            LatLngNew(56.417096, 40.439856),
            LatLngNew(56.416867, 40.440296),
            LatLngNew(56.416704, 40.440511),
            LatLngNew(56.41625, 40.440833),
            LatLngNew(56.415384, 40.441423),
            LatLngNew(56.415217, 40.441605),
            LatLngNew(56.415161, 40.441804),
            LatLngNew(56.415161, 40.441997),
            LatLngNew(56.415033, 40.442683),
            LatLngNew(56.41501, 40.445993),
            LatLngNew(56.415042, 40.44631),
            LatLngNew(56.41528, 40.44632),
            LatLngNew(56.415695, 40.446224),
            LatLngNew(56.417274, 40.445653),
            LatLngNew(56.417642, 40.445546),
            LatLngNew(56.418817, 40.448319),
            LatLngNew(56.418909, 40.448556),
            LatLngNew(56.418939, 40.449044),
            LatLngNew(56.418823, 40.449044),
            LatLngNew(56.417803, 40.44877),
            LatLngNew(56.409255, 40.44665),
            LatLngNew(56.409576, 40.446489),
            LatLngNew(56.409789, 40.444676),
            LatLngNew(56.411724, 40.44444),
            LatLngNew(56.412015, 40.440642),
            LatLngNew(56.412071, 40.440574),
            LatLngNew(56.412644, 40.44064),
            LatLngNew(56.413019, 40.440744),
            LatLngNew(56.413136, 40.440609),
            LatLngNew(56.413393, 40.440129),
            LatLngNew(56.413657, 40.439447),
            LatLngNew(56.414411, 40.438664),
            LatLngNew(56.413643, 40.436576),
            LatLngNew(56.413699, 40.436351),
            LatLngNew(56.414257, 40.435643),
            LatLngNew(56.415254, 40.434516),
            LatLngNew(56.416085, 40.436635),
            LatLngNew(56.417568, 40.437032),
            LatLngNew(56.41783, 40.437515),
            LatLngNew(56.419545, 40.439586),
            LatLngNew(56.419782, 40.440508),
            LatLngNew(56.419746, 40.440883),
            LatLngNew(56.420357, 40.441532),
            LatLngNew(56.420594, 40.442085),
            LatLngNew(56.420959, 40.440814),
            LatLngNew(56.421434, 40.439151),
            LatLngNew(56.421499, 40.437911),
            LatLngNew(56.421648, 40.437064),
            LatLngNew(56.42174, 40.43605),
            LatLngNew(56.421968, 40.4351),
            LatLngNew(56.42201, 40.435143),
            LatLngNew(56.421861, 40.435717),
            LatLngNew(56.422374, 40.436071),
            LatLngNew(56.422487, 40.43609),
            LatLngNew(56.422802, 40.435849),
            LatLngNew(56.423055, 40.435251),
            LatLngNew(56.423183, 40.435042),
            LatLngNew(56.423554, 40.435106),
            LatLngNew(56.424062, 40.435023),
            LatLngNew(56.424214, 40.435168),
            LatLngNew(56.424508, 40.435811),
            LatLngNew(56.424769, 40.436058),
            LatLngNew(56.42508, 40.436198),
            LatLngNew(56.424985, 40.43682),
            LatLngNew(56.424813, 40.437421),
            LatLngNew(56.42457, 40.438343),
            LatLngNew(56.424223, 40.439169),
            LatLngNew(56.423718, 40.440065),
            LatLngNew(56.423558, 40.440301),
            LatLngNew(56.423348, 40.440483),
            LatLngNew(56.423154, 40.44084)

        )
    }
}