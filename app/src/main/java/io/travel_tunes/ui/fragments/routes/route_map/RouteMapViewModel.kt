package io.travel_tunes.ui.fragments.routes.route_map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.BaseViewModelFactory

class RouteMapViewModel(
    routeItemInfo: RouteItemInfo
) : ViewModel() {

    val routeInfo = MutableLiveData(routeItemInfo)

}


class RouteMapViewModelFactory(
    private val routeItemInfo: RouteItemInfo
) : BaseViewModelFactory<RouteMapViewModel>() {
    override fun getViewModel(): RouteMapViewModel {
        return RouteMapViewModel(routeItemInfo)
    }
}