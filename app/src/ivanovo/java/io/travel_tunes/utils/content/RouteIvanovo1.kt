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
data class RouteIvanovo1(
    private val tag: String = "ivanovo_1",
    private val jsonDescription: Int = R.raw.route_ivanovo_1,
    private val pictureMainRes: Int = R.drawable.route_ivanovo_1_main,
    private val pictureDescriptionRes: Int = R.drawable.route_ivanovo_1_description,
    private val audioRes: Int = R.raw.route_ivanovo_1_description
) : RouteSealedInfo() {

    @IgnoredOnParcel
    private var routeInfo: RouteItemInfo? = null
    override fun getRouteTag() = tag

    override fun getRouteMainPictureRes() = pictureMainRes
    override fun getRouteDescriptionPictureRes() = pictureDescriptionRes
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
            R.drawable.route_ivanovo_1_32,
            R.drawable.route_ivanovo_1_33
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
            R.raw.route_ivanovo_1_32,
            R.raw.route_ivanovo_1_33
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
            LatLngNew(57.0172738, 40.9794269),
            LatLngNew(57.01664, 40.9790889),
            LatLngNew(57.016459, 40.9786812),
            LatLngNew(57.0164415, 40.9781126),
            LatLngNew(57.0164327, 40.977442),
            LatLngNew(57.0165378, 40.9728769),
            LatLngNew(57.0165261, 40.9718308),
            LatLngNew(57.0163743, 40.970066),
            LatLngNew(57.0160735, 40.969905),
            LatLngNew(57.0160095, 40.9695579),
            LatLngNew(57.0151334, 40.9688552),
            LatLngNew(57.0148559, 40.9687801),
            LatLngNew(57.0146778, 40.9689089),
            LatLngNew(57.0143814, 40.9696644),
            LatLngNew(57.0144369, 40.970217),
            LatLngNew(57.0144749, 40.9706944),
            LatLngNew(57.0121121, 40.9716654),
            LatLngNew(57.0106254, 40.972588),
            LatLngNew(57.0107656, 40.9747284),
            LatLngNew(57.0107189, 40.9747338),
            LatLngNew(57.0105582, 40.9726095),
            LatLngNew(57.0104239, 40.9724486),
            LatLngNew(57.0102019, 40.9724056),
            LatLngNew(57.0098192, 40.9724978),
            LatLngNew(57.0093964, 40.9726024),
            LatLngNew(57.0092869, 40.9727472),
            LatLngNew(57.0079392, 40.9730331),
            LatLngNew(57.0078983, 40.972325),
            LatLngNew(57.0072673, 40.9724645),
            LatLngNew(57.0070073, 40.9707586),
            LatLngNew(57.0070424, 40.9703388),
            LatLngNew(57.0066928, 40.9703879),
            LatLngNew(57.005945, 40.9705287),
            LatLngNew(57.005906, 40.970607),
            LatLngNew(57.0059717, 40.9716719),
            LatLngNew(57.0060499, 40.9728963),
            LatLngNew(57.0060718, 40.9733442),
            LatLngNew(57.0059469, 40.9733549),
            LatLngNew(57.0056818, 40.973418),
            LatLngNew(57.0053116, 40.9734998),
            LatLngNew(57.0046733, 40.9736674),
            LatLngNew(57.0044732, 40.9737559),
            LatLngNew(57.0044206, 40.9739021),
            LatLngNew(57.0043877, 40.9736218),
            LatLngNew(57.0035785, 40.9737023),
            LatLngNew(57.0033988, 40.9737734),
            LatLngNew(57.0027831, 40.973819),
            LatLngNew(57.0015875, 40.9739195),
            LatLngNew(57.0015009, 40.973911),
            LatLngNew(57.0007873, 40.9739405),
            LatLngNew(57.0006872, 40.9741082),
            LatLngNew(57.0005273, 40.9742919),
            LatLngNew(57.0002946, 40.9748402),
            LatLngNew(56.9998569, 40.9747077),
            LatLngNew(56.9994683, 40.974131),
            LatLngNew(56.9994493, 40.9739808),
            LatLngNew(56.9994317, 40.9738682),
            LatLngNew(56.9993631, 40.973844),
            LatLngNew(56.9988664, 40.9737609),
            LatLngNew(56.9987612, 40.9737958),
            LatLngNew(56.9986779, 40.973887),
            LatLngNew(56.9981389, 40.9750349),
            LatLngNew(56.997984, 40.9753166),
            LatLngNew(56.9976582, 40.9759576),
            LatLngNew(56.9968562, 40.9772424),
            LatLngNew(56.9964749, 40.9777842),
            LatLngNew(56.9960702, 40.9783529),
            LatLngNew(56.9958349, 40.978715),
            LatLngNew(56.9958145, 40.9790207),
            LatLngNew(56.9962309, 40.9798656),
            LatLngNew(56.9962528, 40.9799782),
            LatLngNew(56.9966414, 40.9802679),
            LatLngNew(56.9966297, 40.9803108),
            LatLngNew(56.9963039, 40.9800641),
            LatLngNew(56.9962382, 40.9800078),
            LatLngNew(56.9961374, 40.9799085),
            LatLngNew(56.9957531, 40.9791468),
            LatLngNew(56.9955018, 40.9797422),
            LatLngNew(56.9955632, 40.9801741),
            LatLngNew(56.9957283, 40.9805603),
            LatLngNew(56.9958121, 40.9808422),
            LatLngNew(56.996078, 40.9813867),
            LatLngNew(56.9962262, 40.9811614),
            LatLngNew(56.9964301, 40.9814792),
            LatLngNew(56.9966616, 40.9810648),
            LatLngNew(56.9967332, 40.9810394),
            LatLngNew(56.996788, 40.9810568),
            LatLngNew(56.9968676, 40.9811158),
            LatLngNew(56.9970291, 40.9813411),
            LatLngNew(56.9974016, 40.9819527),
            LatLngNew(56.9964717, 40.9835419),
            LatLngNew(56.9963366, 40.9838758),
            LatLngNew(56.9962664, 40.9843103),
            LatLngNew(56.9963249, 40.9844645),
            LatLngNew(56.9963775, 40.9845222),
            LatLngNew(56.9964783, 40.9842406),
            LatLngNew(56.9973022, 40.985571),
            LatLngNew(56.9973563, 40.9855884),
            LatLngNew(56.9974827, 40.9858311),
            LatLngNew(56.9984476, 40.9838852),
            LatLngNew(56.998712, 40.9842285),
            LatLngNew(56.9988559, 40.9844377),
            LatLngNew(56.9994994, 40.9855401),
            LatLngNew(57.0002349, 40.9866586),
            LatLngNew(57.0003402, 40.986794),
            LatLngNew(57.0004417, 40.9869321),
            LatLngNew(57.0009734, 40.9875759),
            LatLngNew(57.001397, 40.9880587),
            LatLngNew(57.0026548, 40.9895151),
            LatLngNew(57.0027716, 40.9896493),
            LatLngNew(57.0033734, 40.9904432),
            LatLngNew(57.0038657, 40.9910024),
            LatLngNew(57.0041037, 40.9910614),
            LatLngNew(57.0042922, 40.9911258),
            LatLngNew(57.0053175, 40.9913431),
            LatLngNew(57.0055984, 40.9914358),
            LatLngNew(57.0062003, 40.9913929),
            LatLngNew(57.0066618, 40.9912723),
            LatLngNew(57.0071971, 40.9909879),
            LatLngNew(57.0073451, 40.9909118),
            LatLngNew(57.0076197, 40.9907644),
            LatLngNew(57.007754, 40.9906463),
            LatLngNew(57.0085149, 40.9900885),
            LatLngNew(57.0090481, 40.9896673),
            LatLngNew(57.0091401, 40.9895761),
            LatLngNew(57.0099879, 40.9889619),
            LatLngNew(57.0098477, 40.9883209),
            LatLngNew(57.0090452, 40.9888385),
            LatLngNew(57.0084071, 40.9893244),
            LatLngNew(57.0082772, 40.9894773),
            LatLngNew(57.00697, 40.990459),
            LatLngNew(57.0066034, 40.990577),
            LatLngNew(57.0062273, 40.9905743),
            LatLngNew(57.0060125, 40.9905944),
            LatLngNew(57.0060031, 40.9903195),
            LatLngNew(57.0055693, 40.990416),
            LatLngNew(57.0054371, 40.9904241),
            LatLngNew(57.0053808, 40.9902122),
            LatLngNew(57.0052779, 40.9900902),
            LatLngNew(57.0051238, 40.9899788),
            LatLngNew(57.0050807, 40.9898722),
            LatLngNew(57.0050573, 40.9897227),
            LatLngNew(57.0050252, 40.9893727),
            LatLngNew(57.0050077, 40.9891326),
            LatLngNew(57.0048499, 40.9879846),
            LatLngNew(57.0035645, 40.9810015),
            LatLngNew(57.0036858, 40.98063),
            LatLngNew(57.0033038, 40.978562),
            LatLngNew(57.003222, 40.9781785),
            LatLngNew(57.0031161, 40.9778606),
            LatLngNew(57.0029926, 40.9772102),
            LatLngNew(57.0029488, 40.9770251),
            LatLngNew(57.0028743, 40.9769473),
            LatLngNew(57.0028181, 40.9769085),
            LatLngNew(57.0027648, 40.9766523),
            LatLngNew(57.0028217, 40.9763371),
            LatLngNew(57.0026735, 40.975625),
            LatLngNew(57.0026056, 40.9756036),
            LatLngNew(57.0025486, 40.9755593),
            LatLngNew(57.0025048, 40.9754239),
            LatLngNew(57.0024967, 40.9752643),
            LatLngNew(57.0024865, 40.9751797),
            LatLngNew(57.0024229, 40.9750912),
            LatLngNew(57.001905, 40.9744703)
        )
    }
}