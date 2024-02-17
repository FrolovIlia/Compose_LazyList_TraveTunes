package io.travel_tunes.ui.fragments.routes.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.travel_tunes.data.DataGenerator
import io.travel_tunes.model.InterestingPointEntity
import io.travel_tunes.utils.BaseViewModelFactory

class RoutesViewModel : ViewModel() {

    val routes : MutableLiveData<List<InterestingPointEntity>> = MutableLiveData()
    init {
        showList()
    }

    private fun showList() {
        routes.value = DataGenerator.getDefaultPointsList()
    }

}


class RoutesViewModelFactory: BaseViewModelFactory<RoutesViewModel>() {
    override fun getViewModel(): RoutesViewModel {
        return RoutesViewModel()
    }
}