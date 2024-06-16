package io.travel_tunes.ui.fragments.routes.route_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.travel_tunes.utils.base.BaseViewModelFactory
import io.travel_tunes.utils.content.RouteSealedInfo

class RouteInfoViewModel(
    routeSealedInfo: RouteSealedInfo
) : ViewModel() {
    val routeInfo = MutableLiveData(routeSealedInfo)
}

class RouteInfoViewModelFactory@AssistedInject constructor(
    @Assisted(tag) private val routeSealedInfo: RouteSealedInfo
) : BaseViewModelFactory<RouteInfoViewModel>() {

    companion object {
        private const val tag = "route_sealed_info"
    }
    override fun getViewModel(): RouteInfoViewModel {
        return RouteInfoViewModel(routeSealedInfo)
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(tag) routeSealedInfo: RouteSealedInfo): RouteInfoViewModelFactory
    }
}