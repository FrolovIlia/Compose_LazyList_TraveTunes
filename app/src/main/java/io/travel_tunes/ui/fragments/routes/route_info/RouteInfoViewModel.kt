package io.travel_tunes.ui.fragments.routes.route_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.BaseViewModelFactory

class RouteInfoViewModel(
    private val routeItemInfo: RouteItemInfo
) : ViewModel() {

    val routeInfo = MutableLiveData(routeItemInfo)

}


class RouteInfoViewModelFactory(
    private val routeItemInfo: RouteItemInfo
) : BaseViewModelFactory<RouteInfoViewModel>() {
    override fun getViewModel(): RouteInfoViewModel {
        return RouteInfoViewModel(routeItemInfo)
    }
}