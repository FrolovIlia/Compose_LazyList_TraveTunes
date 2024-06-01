package io.travel_tunes.ui.fragments.routes.list

import io.travel_tunes.utils.base.BaseViewModel
import io.travel_tunes.utils.base.BaseViewModelFactory
import io.travel_tunes.utils.content.ProjectSetup.ROUTES_LIST
import io.travel_tunes.utils.content.RouteSealedInfo
import io.travel_tunes.utils.extencions.SingleLiveEvent
import io.travel_tunes.utils.prefs.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class RoutesViewModel(private val preferences: PreferenceManager) :
    BaseViewModel() {
    val routePaidTagsSet = preferences.routePaidTagsSet
    val routesNew: Flow<List<RouteSealedInfo>> = MutableStateFlow(ROUTES_LIST)

    private var _openRouteInfoScreenEvent = SingleLiveEvent<RouteSealedInfo?>()
    val openRouteInfoScreenEvent
        get() = _openRouteInfoScreenEvent

    fun handleOpenRouteEventFromAdapter(route: RouteSealedInfo) {
        launchAtViewModelScope {
            preferences.setCurrentRouteTag(route.getRouteTag())
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

class RoutesViewModelFactory(
    private val preferenceManager: PreferenceManager
) : BaseViewModelFactory<RoutesViewModel>() {
    override fun getViewModel(): RoutesViewModel {
        return RoutesViewModel(preferenceManager)
    }
}