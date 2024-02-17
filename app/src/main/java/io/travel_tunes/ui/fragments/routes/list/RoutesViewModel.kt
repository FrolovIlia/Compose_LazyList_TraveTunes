package io.travel_tunes.ui.fragments.routes.list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.travel_tunes.R
import io.travel_tunes.data.DataGenerator.getDataFromJson
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.BaseViewModelFactory

class RoutesViewModel : ViewModel() {

    val routes : MutableLiveData<List<RouteItemInfo>> = MutableLiveData()

    fun initRoutes(context: Context) {
        if (routes.value.isNullOrEmpty()) {
            val routesData = getDataFromJson(context = context, rawId = R.raw.route_1)
            routes.value = routesData.getRoutes()
        }
    }
}

class RoutesViewModelFactory: BaseViewModelFactory<RoutesViewModel>() {
    override fun getViewModel(): RoutesViewModel {
        return RoutesViewModel()
    }
}