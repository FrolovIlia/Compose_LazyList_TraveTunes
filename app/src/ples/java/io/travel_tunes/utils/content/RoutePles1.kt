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
data class RoutePles1(
    private val tag: String = "ples_1",
    private val jsonDescription: Int = R.raw.route_ples_1,
    private val pictureMainRes: Int = R.drawable.route_ples_1_15,
    private val pictureDescriptionRes: Int = R.drawable.route_ples_1_19,
    private val audioRes: Int = R.raw.route_ples_1_description
) : RouteSealedInfo() {
    override fun getRouteTag() = tag.lowercase()

    @IgnoredOnParcel
    private var routeInfo: RouteItemInfo? = null


    override fun getRouteMainPictureRes() = pictureMainRes
    override fun getRouteDescriptionPictureRes() = pictureDescriptionRes
    override fun getRouteAudioRes() = audioRes
    override fun getPointPictureResList(): List<Int> {
        return listOf(
            R.drawable.route_ples_1_1,
            R.drawable.route_ples_1_2,
            R.drawable.route_ples_1_3,
            R.drawable.route_ples_1_4,
            R.drawable.route_ples_1_5,
            R.drawable.route_ples_1_6,
            R.drawable.route_ples_1_7,
            R.drawable.route_ples_1_8,
            R.drawable.route_ples_1_9,
            R.drawable.route_ples_1_10,
            R.drawable.route_ples_1_11,
            R.drawable.route_ples_1_12,
            R.drawable.route_ples_1_13,
            R.drawable.route_ples_1_14,
            R.drawable.route_ples_1_15,
            R.drawable.route_ples_1_16,
            R.drawable.route_ples_1_17,
            R.drawable.route_ples_1_18,
            R.drawable.route_ples_1_19,
            R.drawable.route_ples_1_20,
            R.drawable.route_ples_1_21,
            R.drawable.route_ples_1_22,
            R.drawable.route_ples_1_23,
            R.drawable.route_ples_1_24,
            R.drawable.route_ples_1_25,
            R.drawable.route_ples_1_26,
            R.drawable.route_ples_1_27,
            R.drawable.route_ples_1_28,
            R.drawable.route_ples_1_29,
            R.drawable.route_ples_1_30,
            R.drawable.route_ples_1_31,
            R.drawable.route_ples_1_32
        )
    }

    override fun getPointAudioResList(): List<Int> {
        return listOf(
            R.raw.route_ples_1_1,
            R.raw.route_ples_1_2,
            R.raw.route_ples_1_3,
            R.raw.route_ples_1_4,
            R.raw.route_ples_1_5,
            R.raw.route_ples_1_6,
            R.raw.route_ples_1_7,
            R.raw.route_ples_1_8,
            R.raw.route_ples_1_9,
            R.raw.route_ples_1_10,
            R.raw.route_ples_1_11,
            R.raw.route_ples_1_12,
            R.raw.route_ples_1_13,
            R.raw.route_ples_1_14,
            R.raw.route_ples_1_15,
            R.raw.route_ples_1_16,
            R.raw.route_ples_1_17,
            R.raw.route_ples_1_18,
            R.raw.route_ples_1_19,
            R.raw.route_ples_1_20,
            R.raw.route_ples_1_21,
            R.raw.route_ples_1_22,
            R.raw.route_ples_1_23,
            R.raw.route_ples_1_24,
            R.raw.route_ples_1_25,
            R.raw.route_ples_1_26,
            R.raw.route_ples_1_27,
            R.raw.route_ples_1_28,
            R.raw.route_ples_1_29,
            R.raw.route_ples_1_30,
            R.raw.route_ples_1_31,
            R.raw.route_ples_1_32
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

            LatLngNew(57.4562376, 41.512359),
            LatLngNew(57.4563242, 41.5126433),
            LatLngNew(57.4563646, 41.5130457),
            LatLngNew(57.456431, 41.513389),
            LatLngNew(57.4568465, 41.514301),
            LatLngNew(57.4570456, 41.5146014),
            LatLngNew(57.4573575, 41.5152713),
            LatLngNew(57.4574816, 41.5155771),
            LatLngNew(57.4575538, 41.5160009),
            LatLngNew(57.4575999, 41.5162423),
            LatLngNew(57.4576692, 41.5163496),
            LatLngNew(57.4577846, 41.5163603),
            LatLngNew(57.4579895, 41.5164354),
            LatLngNew(57.4581251, 41.5165373),
            LatLngNew(57.458229, 41.5166983),
            LatLngNew(57.4583863, 41.516996),
            LatLngNew(57.4585623, 41.5172454),
            LatLngNew(57.4586402, 41.5172857),
            LatLngNew(57.4587325, 41.5173929),
            LatLngNew(57.4588826, 41.51746),
            LatLngNew(57.4591414, 41.5175947),
            LatLngNew(57.4591948, 41.5176054),
            LatLngNew(57.4594646, 41.5176135),
            LatLngNew(57.4595295, 41.5172111),
            LatLngNew(57.4593809, 41.5171012),
            LatLngNew(57.4591732, 41.5169724),
            LatLngNew(57.4590606, 41.5167954),
            LatLngNew(57.4590145, 41.5166184),
            LatLngNew(57.459052, 41.5163662),
            LatLngNew(57.4591299, 41.5161302),
            LatLngNew(57.4592424, 41.5159049),
            LatLngNew(57.459404, 41.5155883),
            LatLngNew(57.4593751, 41.5151485),
            LatLngNew(57.459228, 41.5146067),
            LatLngNew(57.459013, 41.5145155),
            LatLngNew(57.4588586, 41.5144565),
            LatLngNew(57.4588947, 41.5148051),
            LatLngNew(57.4582267, 41.5151941),
            LatLngNew(57.4577246, 41.5154838),
            LatLngNew(57.4575904, 41.5155321),
            LatLngNew(57.4575268, 41.515519),
            LatLngNew(57.457446, 41.5154064),
            LatLngNew(57.4573608, 41.5151945),
            LatLngNew(57.4573161, 41.5150496),
            LatLngNew(57.4570795, 41.5145507),
            LatLngNew(57.456472, 41.5132955),
            LatLngNew(57.4564417, 41.5131158),
            LatLngNew(57.4564114, 41.5129495),
            LatLngNew(57.4563739, 41.5126678),
            LatLngNew(57.4563595, 41.5124318),
            LatLngNew(57.456384, 41.5122843),
            LatLngNew(57.4564807, 41.5121153),
            LatLngNew(57.4567765, 41.511603),
            LatLngNew(57.4569366, 41.5112704),
            LatLngNew(57.4570694, 41.51086),
            LatLngNew(57.4571343, 41.5107447),
            LatLngNew(57.4572166, 41.510683),
            LatLngNew(57.4574893, 41.5105516),
            LatLngNew(57.4575931, 41.510388),
            LatLngNew(57.4576494, 41.5102109),
            LatLngNew(57.457723, 41.5097764),
            LatLngNew(57.4577244, 41.5096611),
            LatLngNew(57.4576884, 41.5095726),
            LatLngNew(57.4558199, 41.5090441),
            LatLngNew(57.4558285, 41.5089583),
            LatLngNew(57.4565673, 41.5091514),
            LatLngNew(57.456928, 41.5042913),
            LatLngNew(57.4569684, 41.5031943),
            LatLngNew(57.4570088, 41.5025961),
            LatLngNew(57.4571242, 41.502065),
            LatLngNew(57.4571848, 41.5017056),
            LatLngNew(57.4572223, 41.5009707),
            LatLngNew(57.4574503, 41.4964163),
            LatLngNew(57.4574532, 41.4961588),
            LatLngNew(57.4573955, 41.4958262),
            LatLngNew(57.4573118, 41.495558),
            LatLngNew(57.4572772, 41.4953971),
            LatLngNew(57.4574243, 41.4952093),
            LatLngNew(57.4577648, 41.4951932),
            LatLngNew(57.4580592, 41.4952469),
            LatLngNew(57.4583448, 41.4953703),
            LatLngNew(57.4593317, 41.4957297),
            LatLngNew(57.4615737, 41.4967275),
            LatLngNew(57.4619064, 41.4968785),
            LatLngNew(57.4622656, 41.4968597),
            LatLngNew(57.4624084, 41.4969214),
            LatLngNew(57.4624748, 41.4970395),
            LatLngNew(57.4625123, 41.4972084),
            LatLngNew(57.4624921, 41.4974042),
            LatLngNew(57.4623435, 41.4985629),
            LatLngNew(57.4621776, 41.4992147),
            LatLngNew(57.4620377, 41.4996627),
            LatLngNew(57.4619078, 41.5002125),
            LatLngNew(57.4618746, 41.500588),
            LatLngNew(57.4618977, 41.5009421),
            LatLngNew(57.4619713, 41.5014839),
            LatLngNew(57.4620405, 41.5019184),
            LatLngNew(57.4621127, 41.5021973),
            LatLngNew(57.4621834, 41.5024092),
            LatLngNew(57.4624127, 41.5028196),
            LatLngNew(57.4624344, 41.5028947),
            LatLngNew(57.462462, 41.5031854),
            LatLngNew(57.4625053, 41.503577),
            LatLngNew(57.4625586, 41.503805),
            LatLngNew(57.4626077, 41.5039203),
            LatLngNew(57.4626538, 41.5039927),
            LatLngNew(57.4626827, 41.5040356),
            LatLngNew(57.462651, 41.5044433),
            LatLngNew(57.4624591, 41.5055189),
            LatLngNew(57.4619441, 41.5080107),
            LatLngNew(57.4611427, 41.5075695),
            LatLngNew(57.4610435, 41.5077002),
            LatLngNew(57.460877, 41.5094473),
            LatLngNew(57.4607476, 41.510632),
            LatLngNew(57.4606057, 41.51186),
            LatLngNew(57.4608623, 41.5128817),
            LatLngNew(57.4612804, 41.5130679),
            LatLngNew(57.4612184, 41.5135709),
            LatLngNew(57.4608462, 41.5158293),
            LatLngNew(57.4605836, 41.5174762),
            LatLngNew(57.4601018, 41.5202603),
            LatLngNew(57.4597676, 41.5220704),
            LatLngNew(57.4595252, 41.5235242),
            LatLngNew(57.4591212, 41.5290629),
            LatLngNew(57.4590446, 41.5289529),
            LatLngNew(57.4583004, 41.5287384),
            LatLngNew(57.4583465, 41.5282583),
            LatLngNew(57.458402, 41.527738),
            LatLngNew(57.4585326, 41.5268582),
            LatLngNew(57.4586091, 41.5262118),
            LatLngNew(57.4586653, 41.5254098),
            LatLngNew(57.4586956, 41.5247821),
            LatLngNew(57.4587909, 41.5231567),
            LatLngNew(57.4578906, 41.5227812),
            LatLngNew(57.4577593, 41.5228724),
            LatLngNew(57.4576842, 41.5230146),
            LatLngNew(57.4576352, 41.5229851),
            LatLngNew(57.4576814, 41.5228885),
            LatLngNew(57.4578256, 41.522741),
            LatLngNew(57.4575449, 41.5220266),
            LatLngNew(57.4571929, 41.5211093),
            LatLngNew(57.4571712, 41.5208625),
            LatLngNew(57.4571539, 41.5205648),
            LatLngNew(57.4570486, 41.5201598),
            LatLngNew(57.4569909, 41.5201464),
            LatLngNew(57.4569317, 41.5202187),
            LatLngNew(57.4568886, 41.5202912),
            LatLngNew(57.4568322, 41.520597),
            LatLngNew(57.4563315, 41.5219971),
            LatLngNew(57.4562691, 41.5222424),
            LatLngNew(57.4557655, 41.5256703),
            LatLngNew(57.4558593, 41.5257749),
            LatLngNew(57.4559228, 41.5255737),
            LatLngNew(57.4559949, 41.5253886),
            LatLngNew(57.4561046, 41.5253242),
            LatLngNew(57.4562777, 41.5253484),
            LatLngNew(57.4570035, 41.5257051),
            LatLngNew(57.4570915, 41.5258392),
            LatLngNew(57.4570987, 41.5260538),
            LatLngNew(57.457049, 41.5262242),
            LatLngNew(57.4569509, 41.5264964),
            LatLngNew(57.4568398, 41.526719),
            LatLngNew(57.4567424, 41.5268584),
            LatLngNew(57.4565822, 41.5270247),
            LatLngNew(57.4565122, 41.5271066),
            LatLngNew(57.456474, 41.5270891),
            LatLngNew(57.4564509, 41.5270033),
            LatLngNew(57.4564105, 41.5266788),
            LatLngNew(57.4563456, 41.526491),
            LatLngNew(57.4562525, 41.5263421),
            LatLngNew(57.4561537, 41.5262268),
            LatLngNew(57.4560505, 41.5261585),
            LatLngNew(57.4559307, 41.5260404),
            LatLngNew(57.4558579, 41.5259143),
            LatLngNew(57.455842, 41.5258366),
            LatLngNew(57.4557497, 41.5257373),
            LatLngNew(57.4555837, 41.5263945),
            LatLngNew(57.4553702, 41.5268584),
            LatLngNew(57.4548146, 41.5279152),
            LatLngNew(57.4545953, 41.5282559),
            LatLngNew(57.4542201, 41.5290579),
            LatLngNew(57.4535982, 41.5280199),
            LatLngNew(57.4535665, 41.5280896),
            LatLngNew(57.4542014, 41.5291196),
            LatLngNew(57.4526891, 41.5323114)
        )
    }
}