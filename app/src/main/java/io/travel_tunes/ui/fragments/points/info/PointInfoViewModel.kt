package io.travel_tunes.ui.fragments.points.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.travel_tunes.model.route.PointItemFullInfo
import io.travel_tunes.utils.BaseViewModelFactory

class PointInfoViewModel(
    pointItemFullInfo: PointItemFullInfo
) : ViewModel() {

    val pointItemFullInfo = MutableLiveData(pointItemFullInfo)
    fun updateData(pointItemFullInfo: PointItemFullInfo) {
        this.pointItemFullInfo.value = pointItemFullInfo
    }
}


class PointInfoViewModelFactory(
    private val pointItemFullInfo: PointItemFullInfo
) : BaseViewModelFactory<PointInfoViewModel>() {
    override fun getViewModel(): PointInfoViewModel {
        return PointInfoViewModel(pointItemFullInfo)
    }
}