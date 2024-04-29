package io.travel_tunes.ui.fragments.routes.route_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.travel_tunes.utils.base.BaseViewModelFactory
import io.travel_tunes.utils.content.RouteSealedInfo

class RouteInfoViewModel(
    routeSealedInfo: RouteSealedInfo
) : ViewModel() {
    val routeInfo = MutableLiveData(routeSealedInfo)
}

class RouteInfoViewModelFactory(
    private val routeSealedInfo: RouteSealedInfo
) : BaseViewModelFactory<RouteInfoViewModel>() {
    override fun getViewModel(): RouteInfoViewModel {
        return RouteInfoViewModel(routeSealedInfo)
    }
}