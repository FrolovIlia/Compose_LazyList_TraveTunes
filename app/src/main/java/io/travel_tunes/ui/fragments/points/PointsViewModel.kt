package io.travel_tunes.ui.fragments.points

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import io.travel_tunes.InterestingPointRepository
import io.travel_tunes.model.InterestingPointEntity
import io.travel_tunes.utils.BaseViewModelFactory
import kotlinx.coroutines.flow.map

class PointsViewModel(
    private val interestingPointRepository: InterestingPointRepository,
    private val cityCodeName: String
) : ViewModel() {

    val points: LiveData<List<InterestingPointEntity>> = interestingPointRepository.allPoints.map {
        it.filter { point ->
            point.cityCodeName == cityCodeName
        }
    }.asLiveData()
}


class PointsViewModelFactory(
    private val interestingPointRepository: InterestingPointRepository,
    private val cityCodeName: String
) : BaseViewModelFactory<PointsViewModel>() {
    override fun getViewModel(): PointsViewModel {
        return PointsViewModel(interestingPointRepository, cityCodeName)
    }
}