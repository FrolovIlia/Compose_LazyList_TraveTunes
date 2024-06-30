package io.travel_tunes.ui.fragments.routes.route_info

import androidx.lifecycle.ViewModel
import io.travel_tunes.data.repository.DefaultRepository
import io.travel_tunes.utils.base.BaseViewModelFactory
import javax.inject.Inject

class RouteInfoViewModel(
    defaultRepository: DefaultRepository
) : ViewModel() {
    val routeInfoForView = defaultRepository.selectedRouteInfoForView
}

class RouteInfoViewModelFactory @Inject constructor(
    private val defaultRepository: DefaultRepository
) : BaseViewModelFactory<RouteInfoViewModel>() {
    override fun getViewModel(): RouteInfoViewModel {
        return RouteInfoViewModel(defaultRepository)
    }
}