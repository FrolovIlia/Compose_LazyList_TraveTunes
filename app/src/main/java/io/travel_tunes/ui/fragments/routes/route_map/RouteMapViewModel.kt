package io.travel_tunes.ui.fragments.routes.route_map

import android.content.Context
import androidx.annotation.RawRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.travel_tunes.model.route.PointItemFullInfo
import io.travel_tunes.model.route.PointItemInfo
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.base.BaseViewModelFactory
import io.travel_tunes.utils.content.RouteSealedInfo
import io.travel_tunes.utils.extencions.SingleLiveEvent
import io.travel_tunes.utils.prefs.PreferenceManager

class RouteMapViewModel(
    private val routeSealedInfo: RouteSealedInfo,
    private val preferences: PreferenceManager
) : ViewModel() {

    val routeTitle = MutableLiveData<String>()

    val routeItemInfo = MutableLiveData<RouteItemInfo>()

    private val _openPaymentsScreen = SingleLiveEvent<RouteSealedInfo?>()
    val openPaymentsScreen
        get() = _openPaymentsScreen

    private val _openPointInfoScreen = SingleLiveEvent<PointItemFullInfo?>()
    val openPointInfoScreen
        get() = _openPointInfoScreen

    @RawRes
    val routeKmlInfo = MutableLiveData<Int>()

    fun initRouteInfo(context: Context) {
        val routeInfo = routeSealedInfo.getRouteItemInfo(context)
        val updatedRouteInfo = routeInfo.copy(points = routeInfo.getPoints(isPaid = routeSealedInfo.isRoutePaid()))
        val routeKmlRes = routeSealedInfo.getRouteKml()
        routeItemInfo.value = updatedRouteInfo
        routeTitle.value = updatedRouteInfo.getTitle()
        routeKmlInfo.value = routeKmlRes
    }

    fun onMapReady() {
        routeItemInfo.value = routeItemInfo.value
        routeKmlInfo.value = routeKmlInfo.value
    }

    fun handleOnMarkerPointClick(pointItemInfo: PointItemInfo) {
        when {
            !pointItemInfo.isEnabled() -> {
                if (_openPaymentsScreen.value != routeSealedInfo) {
                    _openPaymentsScreen.postValue(routeSealedInfo)
                }
            }
            !pointItemInfo.isSelected() -> {
                val currentRouteInfo = routeItemInfo.value
                val updatedPoints = currentRouteInfo?.getPoints().orEmpty().map { point ->
                    point.copy(isSelected = pointItemInfo.getId() == point.getId())
                }
                routeItemInfo.value = currentRouteInfo?.copy(points = updatedPoints)
                val pointItemFullInfo = getPointItemFullInfo(pointItemInfo.getId())
                if (_openPointInfoScreen.value != pointItemFullInfo) {
                    _openPointInfoScreen.postValue(pointItemFullInfo)
                }
            }
        }
    }

    fun handleOnMapClick() {
        val currentRouteInfo = routeItemInfo.value
        val updatedPoints = currentRouteInfo?.getPoints().orEmpty().map { point ->
            point.copy(isSelected = false)
        }
        routeItemInfo.value = currentRouteInfo?.copy(points = updatedPoints)

        // скрыть плашку если есть
    }

    private fun getPointItemFullInfo(id: String) = routeSealedInfo.getPointItemFullInfo(id)

    fun bottomSheetIsHidden() {
        handleOnMapClick()
    }

    fun clearOpenPointInfoScreen() {
        _openPointInfoScreen.call()
    }

    fun clearOpenPaymentsScreen() {
        _openPaymentsScreen.call()
    }
}

class RouteMapViewModelFactory(
    private val routeSealedInfo: RouteSealedInfo,
    private val preferences: PreferenceManager
) : BaseViewModelFactory<RouteMapViewModel>() {
    override fun getViewModel(): RouteMapViewModel {
        return RouteMapViewModel(routeSealedInfo, preferences)
    }
}