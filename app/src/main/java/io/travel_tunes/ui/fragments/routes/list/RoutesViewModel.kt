package io.travel_tunes.ui.fragments.routes.list

import io.travel_tunes.data.repository.DefaultRepository
import io.travel_tunes.utils.base.BaseViewModel
import io.travel_tunes.utils.base.BaseViewModelFactory
import io.travel_tunes.utils.content.ProjectSetup.ROUTES_LIST
import io.travel_tunes.utils.content.RouteSealedInfo
import io.travel_tunes.utils.extencions.SingleLiveEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class RoutesViewModel(private val defaultRepository: DefaultRepository) :
    BaseViewModel() {
    val routePaidTagsSet = defaultRepository.routePaidTagsSet
    val routesNew: Flow<List<RouteSealedInfo>> = MutableStateFlow(ROUTES_LIST)

    private var _openRouteInfoScreenEvent = SingleLiveEvent<RouteSealedInfo?>()
    val openRouteInfoScreenEvent
        get() = _openRouteInfoScreenEvent

    fun handleOpenRouteEventFromAdapter(route: RouteSealedInfo) {
        launchAtViewModelScope {
            defaultRepository.setCurrentRouteTag(route.getRouteTag())
            startOpenRouteInfoScreenEvent(route)
        }
    }

    fun clearOpenRouteInfoScreenEvent() {
        _openRouteInfoScreenEvent.call()
    }

    private fun startOpenRouteInfoScreenEvent(route: RouteSealedInfo) {
        if (route != _openRouteInfoScreenEvent.value) {
            _openRouteInfoScreenEvent.postValue(route)
        }
    }
}

class RoutesViewModelFactory @Inject constructor(
    private val defaultRepository: DefaultRepository
) : BaseViewModelFactory<RoutesViewModel>() {
    override fun getViewModel(): RoutesViewModel {
        return RoutesViewModel(defaultRepository)
    }
}