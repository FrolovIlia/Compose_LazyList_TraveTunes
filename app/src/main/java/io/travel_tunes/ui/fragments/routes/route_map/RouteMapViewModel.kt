package io.travel_tunes.ui.fragments.routes.route_map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.travel_tunes.utils.BaseViewModelFactory
import io.travel_tunes.utils.content.RouteSealedInfo

class RouteMapViewModel(
    routeSealedInfo: RouteSealedInfo
) : ViewModel() {

    val routeInfo = MutableLiveData(routeSealedInfo)


    fun onMapReady() {
        routeInfo.value = routeInfo.value
    }
}

class RouteMapViewModelFactory(
    private val routeSealedInfo: RouteSealedInfo
) : BaseViewModelFactory<RouteMapViewModel>() {
    override fun getViewModel(): RouteMapViewModel {
        return RouteMapViewModel(routeSealedInfo)
    }
}