package io.travel_tunes.ui.fragments.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import io.travel_tunes.InterestingPointRepository
import io.travel_tunes.model.CityEntity
import io.travel_tunes.utils.BaseViewModelFactory

class CitiesViewModel(
    private val interestingPointRepository: InterestingPointRepository
) : ViewModel() {

    val cities: LiveData<List<CityEntity>> =
        interestingPointRepository.allCities.asLiveData()
}


class CitiesViewModelFactory(
    private val interestingPointRepository: InterestingPointRepository
) : BaseViewModelFactory<CitiesViewModel>() {
    override fun getViewModel(): CitiesViewModel {
        return CitiesViewModel(interestingPointRepository)
    }
}