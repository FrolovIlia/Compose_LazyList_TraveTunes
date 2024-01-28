package com.example.compose_lazylist_travetunes.ui.fragments.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.compose_lazylist_travetunes.InterestingPointRepository
import com.example.compose_lazylist_travetunes.model.CityEntity
import com.example.compose_lazylist_travetunes.utils.BaseViewModelFactory

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