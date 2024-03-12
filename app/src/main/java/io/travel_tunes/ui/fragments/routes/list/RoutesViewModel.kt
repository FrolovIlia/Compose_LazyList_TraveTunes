package io.travel_tunes.ui.fragments.routes.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.travel_tunes.utils.BaseViewModelFactory
import io.travel_tunes.utils.content.RouteIvanovo1
import io.travel_tunes.utils.content.RouteIvanovo2
import io.travel_tunes.utils.content.RouteSealedInfo

class RoutesViewModel : ViewModel() {
    val routesNew: MutableLiveData<List<RouteSealedInfo>> = MutableLiveData()

    init {
        routesNew.value = listOf(RouteIvanovo1(), RouteIvanovo2())
    }
}

class RoutesViewModelFactory : BaseViewModelFactory<RoutesViewModel>() {
    override fun getViewModel(): RoutesViewModel {
        return RoutesViewModel()
    }
}