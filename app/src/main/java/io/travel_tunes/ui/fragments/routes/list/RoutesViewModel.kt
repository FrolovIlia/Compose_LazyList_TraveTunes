package io.travel_tunes.ui.fragments.routes.list

import android.content.Context
import io.travel_tunes.data.repository.DefaultRepository
import io.travel_tunes.utils.Mapper
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

    private var _openRouteInfoScreenEvent = SingleLiveEvent<Boolean?>()
    val openRouteInfoScreenEvent
        get() = _openRouteInfoScreenEvent

    fun handleOpenRouteEventFromAdapter(route: RouteSealedInfo, context: Context) {
        launchAtViewModelScope {
            val routeInfoForView = Mapper.mapRouteSealedInfoToRouteView(route, context)
            defaultRepository.setCurrentRouteTag(route.getRouteTag())
            defaultRepository.setSelectedRouteInfoForView(routeInfoForView)
            startOpenRouteInfoScreenEvent()
        }
    }

    fun clearOpenRouteInfoScreenEvent() {
        _openRouteInfoScreenEvent.call()
    }

    private fun startOpenRouteInfoScreenEvent() {
        if (true != _openRouteInfoScreenEvent.value) {
            _openRouteInfoScreenEvent.postValue(true)
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