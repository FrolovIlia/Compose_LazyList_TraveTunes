package com.example.compose_lazylist_travetunes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.compose_lazylist_travetunes.InterestingPointRepository
import com.example.compose_lazylist_travetunes.model.InterestingPointEntity
import com.example.compose_lazylist_travetunes.utils.BaseViewModelFactory

class InterestingPointViewModel(
    private val interestingPointRepository: InterestingPointRepository,
    private val selectedCityCodeName: String
) : ViewModel() {

    val interestingPoints: LiveData<List<InterestingPointEntity>> =
        interestingPointRepository.allPoints.asLiveData()
}


class InterestingPointViewModelFactory(
    private val interestingPointRepository: InterestingPointRepository,
    private val selectedCityCodeName: String
) : BaseViewModelFactory<InterestingPointViewModel>() {
    override fun getViewModel(): InterestingPointViewModel {
        return InterestingPointViewModel(interestingPointRepository, selectedCityCodeName)
    }
}