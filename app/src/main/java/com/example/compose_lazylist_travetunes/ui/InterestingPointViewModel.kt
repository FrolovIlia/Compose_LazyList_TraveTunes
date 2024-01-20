package com.example.compose_lazylist_travetunes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.compose_lazylist_travetunes.InterestingPointRepository
import com.example.compose_lazylist_travetunes.R
import com.example.compose_lazylist_travetunes.model.InterestingPointEntity
import com.example.compose_lazylist_travetunes.utils.BaseViewModelFactory

class InterestingPointViewModel(
    private val interestingPointRepository: InterestingPointRepository
) : ViewModel() {

    val interestingPoints: LiveData<List<InterestingPointEntity>> =
        interestingPointRepository.allPoints.asLiveData()

    fun addTestItemToDataSource() {
        val interestingPointEntity = InterestingPointEntity(
            title = R.string.spb_title1,
            description = R.string.spb_description2,
            picture = R.drawable.spb_1,
            id = 234
        )
    }
}


class InterestingPointViewModelFactory(
    private val interestingPointRepository: InterestingPointRepository
) : BaseViewModelFactory<InterestingPointViewModel>() {
    override fun getViewModel(): InterestingPointViewModel {
        return InterestingPointViewModel(interestingPointRepository)
    }
}