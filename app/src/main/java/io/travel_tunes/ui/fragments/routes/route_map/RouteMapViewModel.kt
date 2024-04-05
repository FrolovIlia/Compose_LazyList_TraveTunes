package io.travel_tunes.ui.fragments.routes.route_map

import android.content.Context
import androidx.annotation.RawRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.travel_tunes.model.route.PointItemInfo
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.BaseViewModelFactory
import io.travel_tunes.utils.content.RouteSealedInfo

class RouteMapViewModel(
    private val routeSealedInfo: RouteSealedInfo
) : ViewModel() {

    val routeTitle = MutableLiveData<String>()

    val routeItemInfo = MutableLiveData<RouteItemInfo>()

    @RawRes
    val routeKmlInfo = MutableLiveData<Int>()

    fun initRouteInfo(context: Context) {
        val routeInfo = routeSealedInfo.getRouteItemInfo(context)
        val routeKmlRes = routeSealedInfo.getRouteKml()
        routeItemInfo.value = routeInfo
        routeTitle.value = routeInfo.getTitle()
        routeKmlInfo.value = routeKmlRes
    }

    fun onMapReady() {
        routeItemInfo.value = routeItemInfo.value
        routeKmlInfo.value = routeKmlInfo.value
    }

    fun handleOnMarkerPointClick(pointItemInfo: PointItemInfo) {
        if (!pointItemInfo.isSelected()) {
            val currentRouteInfo = routeItemInfo.value
            val updatedPoints = currentRouteInfo?.getPoints().orEmpty().map { point ->
                point.copy(isSelected = pointItemInfo.getId() == point.getId())
            }
            routeItemInfo.value = currentRouteInfo?.copy(points = updatedPoints)
        }
        // отобразить плашку
    }

    fun handleOnMapClick() {
        val currentRouteInfo = routeItemInfo.value
        val updatedPoints = currentRouteInfo?.getPoints().orEmpty().map { point ->
            point.copy(isSelected = false)
        }
        routeItemInfo.value = currentRouteInfo?.copy(points = updatedPoints)

        // скрыть плашку если есть
    }

    fun getPointItemFullInfo(id: String) = routeSealedInfo.getPointItemFullInfo(id)

    fun bottomSheetIsHidden() {
        handleOnMapClick()
    }
}

class RouteMapViewModelFactory(
    private val routeSealedInfo: RouteSealedInfo
) : BaseViewModelFactory<RouteMapViewModel>() {
    override fun getViewModel(): RouteMapViewModel {
        return RouteMapViewModel(routeSealedInfo)
    }
}