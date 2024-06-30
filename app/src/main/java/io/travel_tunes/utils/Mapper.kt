package io.travel_tunes.utils

import android.content.Context
import io.travel_tunes.model.route.RouteInfoForView
import io.travel_tunes.utils.content.RouteSealedInfo

object Mapper {
fun mapRouteSealedInfoToRouteView(
    routeSealedInfo: RouteSealedInfo,
    context: Context
): RouteInfoForView =
    RouteInfoForView(
        isPaid = routeSealedInfo.isRoutePaid(),
        freePointsCount = routeSealedInfo.getCountFreePoints(),
        routeTag = routeSealedInfo.getRouteTag(),
        routeKmlRes = routeSealedInfo.getRouteKml(),
        routePictureMainRes = routeSealedInfo.getRouteMainPictureRes(),
        routePictureDescriptionRes = routeSealedInfo.getRouteDescriptionPictureRes(),
        routeAudioRes = routeSealedInfo.getRouteAudioRes(),
        routePointsPicturesResList = routeSealedInfo.getPointPictureResList(),
        routePointsAudiosResList = routeSealedInfo.getPointAudioResList(),
        routeItemInfo = routeSealedInfo.getRouteItemInfo(context)
    )
}