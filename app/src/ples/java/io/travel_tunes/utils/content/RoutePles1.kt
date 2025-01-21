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
    //Используем общее изображение, но в данном случае первая была очень невзрачная, так что выбрана что симпотичнее
    private val pictureMainRes: Int = R.drawable.route_ples_1_4,
    //Используем изображение первой точки маршрута в описании маршрута, но в данном случае первая была очень невзрачная, так что выбрана что симпотичнее
    private val pictureDescriptionRes: Int = R.drawable.route_ples_1_4,
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
            LatLngNew(57.4563242, 41.512643),
            LatLngNew(57.4563646, 41.513045),
            LatLngNew(57.456431, 41.513389),
            LatLngNew(57.4568465, 41.514301),
            LatLngNew(57.4570456, 41.514601),
            LatLngNew(57.4573575, 41.515271),
            LatLngNew(57.4574816, 41.515577),
            LatLngNew(57.4575538, 41.516000),
            LatLngNew(57.4575999, 41.516242),
            LatLngNew(57.4576692, 41.516349),
            LatLngNew(57.4577846, 41.516360),
            LatLngNew(57.4579895, 41.516435),
            LatLngNew(57.4581251, 41.516537),
            LatLngNew(57.458229, 41.516698),
            LatLngNew(57.4583863, 41.516996),
            LatLngNew(57.4585623, 41.517245),
            LatLngNew(57.4586402, 41.517285),
            LatLngNew(57.4587325, 41.517392),
            LatLngNew(57.4588826, 41.51746),
            LatLngNew(57.4591414, 41.517594),
            LatLngNew(57.4591948, 41.517605),
            LatLngNew(57.4594646, 41.517613),
            LatLngNew(57.4595295, 41.517211),
            LatLngNew(57.4593809, 41.517101),
            LatLngNew(57.4591732, 41.516972),
            LatLngNew(57.4590606, 41.516795),
            LatLngNew(57.4590145, 41.516618),
            LatLngNew(57.459052, 41.516366),
            LatLngNew(57.4591299, 41.516130),
            LatLngNew(57.4592424, 41.515904),
            LatLngNew(57.4594213, 41.515625),
            LatLngNew(57.4593751, 41.515148),
            LatLngNew(57.4592915, 41.514896),
            LatLngNew(57.4592496, 41.514853),
            LatLngNew(57.4592049, 41.514842),
            LatLngNew(57.4590029, 41.514869),
            LatLngNew(57.4586942, 41.514960),
            LatLngNew(57.4582267, 41.515239),
            LatLngNew(57.4577246, 41.515507),
            LatLngNew(57.4575745, 41.515572),
            LatLngNew(57.4575311, 41.515540),
            LatLngNew(57.457446, 41.515406),
            LatLngNew(57.4573608, 41.515194),
            LatLngNew(57.4573161, 41.515049),
            LatLngNew(57.4570795, 41.514550),
            LatLngNew(57.456472, 41.513295),
            LatLngNew(57.4564417, 41.513115),
            LatLngNew(57.4564114, 41.512949),
            LatLngNew(57.4563739, 41.512667),
            LatLngNew(57.4563595, 41.512431),
            LatLngNew(57.456384, 41.512284),
            LatLngNew(57.4564807, 41.512115),
            LatLngNew(57.4567765, 41.511603),
            LatLngNew(57.4569366, 41.511270),
            LatLngNew(57.4570694, 41.51086),
            LatLngNew(57.4571343, 41.510744),
            LatLngNew(57.4572166, 41.510683),
            LatLngNew(57.4574893, 41.510551),
            LatLngNew(57.4575931, 41.510388),
            LatLngNew(57.4576494, 41.510210),
            LatLngNew(57.457723, 41.509776),
            LatLngNew(57.4577417, 41.509671),
            LatLngNew(57.4580274, 41.509843),
            LatLngNew(57.4580476, 41.509704),
            LatLngNew(57.4577735, 41.509602),
            LatLngNew(57.4565875, 41.509226),
            LatLngNew(57.456928, 41.504291),
            LatLngNew(57.4569684, 41.503194),
            LatLngNew(57.4570088, 41.502596),
            LatLngNew(57.4571242, 41.502065),
            LatLngNew(57.4571848, 41.501705),
            LatLngNew(57.4572223, 41.500970),
            LatLngNew(57.4574503, 41.496416),
            LatLngNew(57.4574532, 41.496158),
            LatLngNew(57.4573955, 41.495826),
            LatLngNew(57.4573118, 41.495558),
            LatLngNew(57.4572772, 41.495397),
            LatLngNew(57.4574243, 41.495209),
            LatLngNew(57.4577648, 41.495193),
            LatLngNew(57.4580592, 41.495246),
            LatLngNew(57.4583448, 41.495370),
            LatLngNew(57.4593317, 41.495729),
            LatLngNew(57.4615737, 41.496727),
            LatLngNew(57.4619064, 41.496878),
            LatLngNew(57.4622656, 41.496859),
            LatLngNew(57.4624084, 41.496921),
            LatLngNew(57.4624748, 41.497039),
            LatLngNew(57.4625123, 41.497208),
            LatLngNew(57.4624921, 41.497404),
            LatLngNew(57.4623435, 41.498562),
            LatLngNew(57.4621776, 41.499214),
            LatLngNew(57.4620377, 41.499662),
            LatLngNew(57.4619078, 41.500212),
            LatLngNew(57.4618746, 41.500588),
            LatLngNew(57.4618977, 41.500942),
            LatLngNew(57.4619713, 41.501483),
            LatLngNew(57.4620405, 41.501918),
            LatLngNew(57.4621127, 41.502197),
            LatLngNew(57.4621834, 41.502409),
            LatLngNew(57.4624127, 41.502819),
            LatLngNew(57.4624344, 41.502894),
            LatLngNew(57.462462, 41.503185),
            LatLngNew(57.4625053, 41.503577),
            LatLngNew(57.4625586, 41.503805),
            LatLngNew(57.4626077, 41.503920),
            LatLngNew(57.4626538, 41.503992),
            LatLngNew(57.4626827, 41.504035),
            LatLngNew(57.462651, 41.504443),
            LatLngNew(57.4624591, 41.505518),
            LatLngNew(57.4619441, 41.508010),
            LatLngNew(57.4615646, 41.510851),
            LatLngNew(57.4612184, 41.513570),
            LatLngNew(57.4608462, 41.515829),
            LatLngNew(57.4605836, 41.517476),
            LatLngNew(57.4601018, 41.520260),
            LatLngNew(57.4597676, 41.522070),
            LatLngNew(57.4595252, 41.523524),
            LatLngNew(57.4590866, 41.529054),
            LatLngNew(57.4590123, 41.529980),
            LatLngNew(57.4588119, 41.530621),
            LatLngNew(57.458571, 41.531600),
            LatLngNew(57.4580553, 41.534306),
            LatLngNew(57.4579206, 41.534902),
            LatLngNew(57.4579542, 41.535639),
            LatLngNew(57.4579205, 41.535655),
            LatLngNew(57.4578724, 41.534894),
            LatLngNew(57.4579897, 41.534247),
            LatLngNew(57.4585329, 41.531506),
            LatLngNew(57.4587126, 41.530814),
            LatLngNew(57.4589623, 41.529950),
            LatLngNew(57.4590331, 41.529060),
            LatLngNew(57.4582917, 41.528840),
            LatLngNew(57.4583465, 41.528258),
            LatLngNew(57.4585326, 41.526858),
            LatLngNew(57.4586091, 41.526211),
            LatLngNew(57.4586653, 41.525409),
            LatLngNew(57.4586956, 41.524782),
            LatLngNew(57.4587909, 41.523156),
            LatLngNew(57.4578906, 41.522781),
            LatLngNew(57.4577593, 41.522872),
            LatLngNew(57.4576842, 41.523014),
            LatLngNew(57.4576352, 41.522985),
            LatLngNew(57.4576814, 41.522888),
            LatLngNew(57.4578256, 41.522741),
            LatLngNew(57.4575449, 41.522026),
            LatLngNew(57.4571929, 41.521109),
            LatLngNew(57.4571712, 41.520862),
            LatLngNew(57.4571539, 41.520564),
            LatLngNew(57.4570486, 41.520159),
            LatLngNew(57.4569909, 41.520146),
            LatLngNew(57.4568913, 41.520285),
            LatLngNew(57.4568322, 41.520597),
            LatLngNew(57.4563315, 41.521997),
            LatLngNew(57.4562691, 41.522242),
            LatLngNew(57.4557655, 41.525670),
            LatLngNew(57.4558593, 41.525774),
            LatLngNew(57.4559228, 41.525573),
            LatLngNew(57.4559949, 41.525388),
            LatLngNew(57.4561046, 41.525324),
            LatLngNew(57.4562777, 41.525348),
            LatLngNew(57.4570035, 41.525705),
            LatLngNew(57.4570915, 41.525839),
            LatLngNew(57.4570987, 41.526053),
            LatLngNew(57.4570728, 41.526241),
            LatLngNew(57.4569747, 41.526515),
            LatLngNew(57.4568607, 41.526740),
            LatLngNew(57.4567424, 41.526896),
            LatLngNew(57.4565894, 41.527046),
            LatLngNew(57.4565331, 41.527097),
            LatLngNew(57.456474, 41.527089),
            LatLngNew(57.4564509, 41.527003),
            LatLngNew(57.4564105, 41.526678),
            LatLngNew(57.4563456, 41.526491),
            LatLngNew(57.4562489, 41.526356),
            LatLngNew(57.4560267, 41.526182),
            LatLngNew(57.4559242, 41.526053),
            LatLngNew(57.4558579, 41.525914),
            LatLngNew(57.455842, 41.525836),
            LatLngNew(57.4557497, 41.525737),
            LatLngNew(57.4555837, 41.526394),
            LatLngNew(57.4554048, 41.526893),
            LatLngNew(57.4544986, 41.528550),
            LatLngNew(57.4542201, 41.529057),
            LatLngNew(57.4535982, 41.528019),
            LatLngNew(57.4535665, 41.528089),
            LatLngNew(57.4542014, 41.529119),
            LatLngNew(57.4526891, 41.532311)
        )
    }
}