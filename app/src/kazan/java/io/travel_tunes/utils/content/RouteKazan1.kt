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
data class RouteKazan1(
    private val tag: String = "kazan_1",
    private val jsonDescription: Int = R.raw.route_suzdal_1,
    //Используем общее изображение, но в данном случае первая была очень невзрачная, так что выбрана что симпотичнее
    private val pictureMainRes: Int = R.drawable.route_kazan_1_4,
    //Используем изображение первой точки маршрута в описании маршрута, но в данном случае первая была очень невзрачная, так что выбрана что симпотичнее
    private val pictureDescriptionRes: Int = R.drawable.route_kazan_1_7,
    private val audioRes: Int = R.raw.route_kazan_1_description
) : RouteSealedInfo() {
    override fun getRouteTag() = tag.lowercase()

    @IgnoredOnParcel
    private var routeInfo: RouteItemInfo? = null


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

    override fun getRoutePolygon(): List<LatLngNew> {
        return listOf(
            LatLngNew(55.7725396, 49.141162),
            LatLngNew(55.7732208, 49.1398412),
            LatLngNew(55.7733517, 49.1397495),
            LatLngNew(55.7735861, 49.1392369),
            LatLngNew(55.7735399, 49.1391588),
            LatLngNew(55.7780882, 49.1283303),
            LatLngNew(55.7783888, 49.1286462),
            LatLngNew(55.7785588, 49.1287809),
            LatLngNew(55.7788413, 49.1287428),
            LatLngNew(55.7790977, 49.1289037),
            LatLngNew(55.7797645, 49.1297674),
            LatLngNew(55.7802362, 49.1303956),
            LatLngNew(55.7803377, 49.130615),
            LatLngNew(55.7807309, 49.1312647),
            LatLngNew(55.7793723, 49.1339677),
            LatLngNew(55.7786362, 49.1355127),
            LatLngNew(55.7792184, 49.1363763),
            LatLngNew(55.7794929, 49.1367304),
            LatLngNew(55.7797746, 49.1371333),
            LatLngNew(55.7801245, 49.1364038),
            LatLngNew(55.7804432, 49.1355556),
            LatLngNew(55.7809802, 49.1344076),
            LatLngNew(55.7815624, 49.1329753),
            LatLngNew(55.7824403, 49.1310924),
            LatLngNew(55.7828867, 49.1299873),
            LatLngNew(55.7831251, 49.1295474),
            LatLngNew(55.7826545, 49.1289144),
            LatLngNew(55.782214, 49.1282063),
            LatLngNew(55.7824064, 49.1277973),
            LatLngNew(55.7825987, 49.1274097),
            LatLngNew(55.7829629, 49.1266734),
            LatLngNew(55.7829188, 49.1263862),
            LatLngNew(55.7824558, 49.1254179),
            LatLngNew(55.7833321, 49.1238059),
            LatLngNew(55.7833864, 49.1238917),
            LatLngNew(55.7835523, 49.123889),
            LatLngNew(55.7836729, 49.1236852),
            LatLngNew(55.7836639, 49.1233741),
            LatLngNew(55.7836021, 49.1232829),
            LatLngNew(55.783958, 49.122615),
            LatLngNew(55.783949, 49.1210325),
            LatLngNew(55.7839324, 49.1207267),
            LatLngNew(55.7844753, 49.119855),
            LatLngNew(55.784599, 49.1199408),
            LatLngNew(55.7849384, 49.120091),
            LatLngNew(55.7851065, 49.1201138),
            LatLngNew(55.7850779, 49.1199489),
            LatLngNew(55.7849406, 49.1194956),
            LatLngNew(55.78478, 49.1192676),
            LatLngNew(55.784596, 49.1190007),
            LatLngNew(55.7824339, 49.11603),
            LatLngNew(55.7807476, 49.1180202),
            LatLngNew(55.780286, 49.1185835),
            LatLngNew(55.7801798, 49.1182704),
            LatLngNew(55.7802462, 49.1182087),
            LatLngNew(55.7800275, 49.116822),
            LatLngNew(55.7790908, 49.118265),
            LatLngNew(55.7790033, 49.1183509),
            LatLngNew(55.778237, 49.1152824),
            LatLngNew(55.7773802, 49.1160334),
            LatLngNew(55.7773077, 49.115776),
            LatLngNew(55.7781397, 49.1150587),
            LatLngNew(55.7795818, 49.1135299),
            LatLngNew(55.7797437, 49.1132869),
            LatLngNew(55.7800016, 49.1130375),
            LatLngNew(55.7800816, 49.112949),
            LatLngNew(55.7800997, 49.1128471),
            LatLngNew(55.7800921, 49.1127478),
            LatLngNew(55.780059, 49.1126834),
            LatLngNew(55.7800122, 49.1126164),
            LatLngNew(55.7802547, 49.1121286),
            LatLngNew(55.7805383, 49.112547),
            LatLngNew(55.7806604, 49.1124531),
            LatLngNew(55.7811929, 49.1119087),
            LatLngNew(55.7813151, 49.1116565),
            LatLngNew(55.7813376, 49.1116828),
            LatLngNew(55.7813784, 49.1117158),
            LatLngNew(55.7814342, 49.1117185),
            LatLngNew(55.7815156, 49.1116515),
            LatLngNew(55.781573, 49.1118768),
            LatLngNew(55.7815549, 49.1119465),
            LatLngNew(55.7815956, 49.1120431),
            LatLngNew(55.7833633, 49.1143659),
            LatLngNew(55.7835885, 49.1146373),
            LatLngNew(55.7852325, 49.1166785),
            LatLngNew(55.7853441, 49.1168394),
            LatLngNew(55.785592, 49.1171581),
            LatLngNew(55.7857413, 49.117244),
            LatLngNew(55.7879674, 49.1139506),
            LatLngNew(55.7886913, 49.1149699),
            LatLngNew(55.7896896, 49.1129368),
            LatLngNew(55.7897637, 49.1127414),
            LatLngNew(55.7901196, 49.1132483),
            LatLngNew(55.7909038, 49.1143185),
            LatLngNew(55.7919761, 49.1122458),
            LatLngNew(55.7921359, 49.1123048),
            LatLngNew(55.7921073, 49.1118891),
            LatLngNew(55.7929125, 49.110548),
            LatLngNew(55.7934523, 49.1111997),
            LatLngNew(55.7927798, 49.1130344),
            LatLngNew(55.7934101, 49.113898),
            LatLngNew(55.7940985, 49.1147802),
            LatLngNew(55.794011, 49.1150511),
            LatLngNew(55.7946443, 49.1158034),
            LatLngNew(55.7942433, 49.1182818),
            LatLngNew(55.7946594, 49.118483),
            LatLngNew(55.7946651, 49.1185914),
            LatLngNew(55.7946877, 49.1186826),
            LatLngNew(55.7947239, 49.1187577),
            LatLngNew(55.7947465, 49.118806),
            LatLngNew(55.794742, 49.1188757),
            LatLngNew(55.7947314, 49.1189669),
            LatLngNew(55.7943153, 49.1213514),
            LatLngNew(55.7948732, 49.1214828),
            LatLngNew(55.7948183, 49.1217828),
            LatLngNew(55.7946641, 49.1220024),
            LatLngNew(55.7943097, 49.1226167),
            LatLngNew(55.7942223, 49.1225335),
            LatLngNew(55.7941529, 49.1225147),
            LatLngNew(55.7940685, 49.1225657),
            LatLngNew(55.7940127, 49.1226998),
            LatLngNew(55.7940082, 49.1228232),
            LatLngNew(55.7940881, 49.1230297),
            LatLngNew(55.7941665, 49.1231826),
            LatLngNew(55.7942374, 49.1232819),
            LatLngNew(55.794364, 49.1234026),
            LatLngNew(55.7945661, 49.1234777),
            LatLngNew(55.794738, 49.1235259),
            LatLngNew(55.7946927, 49.1238612),
            LatLngNew(55.794839, 49.1239202),
            LatLngNew(55.7948706, 49.1242233),
            LatLngNew(55.7948254, 49.1247061),
            LatLngNew(55.7947319, 49.1250065),
            LatLngNew(55.7945359, 49.124926),
            LatLngNew(55.7943716, 49.1248804),
            LatLngNew(55.7940293, 49.1248107),
            LatLngNew(55.7934578, 49.1247544),
            LatLngNew(55.7931773, 49.1246498),
            LatLngNew(55.7929903, 49.1245589),
            LatLngNew(55.7929405, 49.1242236),
            LatLngNew(55.7932994, 49.1239232),
            LatLngNew(55.7933582, 49.1234592),
            LatLngNew(55.7932149, 49.1233707),
            LatLngNew(55.7932285, 49.1232661),
            LatLngNew(55.7931531, 49.1232259),
            LatLngNew(55.7930521, 49.12325),
            LatLngNew(55.7927086, 49.1232208),
            LatLngNew(55.7926204, 49.1231926),
            LatLngNew(55.7925435, 49.1233428),
            LatLngNew(55.7924923, 49.123375),
            LatLngNew(55.7924365, 49.123324),
            LatLngNew(55.7922106, 49.1230895),
            LatLngNew(55.7920885, 49.1229219),
            LatLngNew(55.7918388, 49.1226047),
            LatLngNew(55.7918087, 49.1224599),
            LatLngNew(55.791863, 49.1222534),
            LatLngNew(55.7913156, 49.1216713),
            LatLngNew(55.7919896, 49.1199708),
            LatLngNew(55.7918538, 49.1197147),
            LatLngNew(55.7905917, 49.1178245),
            LatLngNew(55.7897864, 49.116684),
            LatLngNew(55.7881637, 49.1199134)
        )
    }
}