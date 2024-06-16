package io.travel_tunes.ui.fragments.points.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.travel_tunes.model.route.PointItemFullInfo
import io.travel_tunes.utils.base.BaseViewModelFactory

class PointInfoViewModel(
    pointItemFullInfo: PointItemFullInfo
) : ViewModel() {

    val pointItemFullInfo = MutableLiveData(pointItemFullInfo)
    fun updateData(pointItemFullInfo: PointItemFullInfo) {
        this.pointItemFullInfo.value = pointItemFullInfo
    }
}


class PointInfoViewModelFactory @AssistedInject constructor(
    @Assisted(tag) private val pointItemFullInfo: PointItemFullInfo
) : BaseViewModelFactory<PointInfoViewModel>() {

    companion object {
        private const val tag = "point_item_full_info"
    }

    override fun getViewModel(): PointInfoViewModel {
        return PointInfoViewModel(pointItemFullInfo)
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(tag) pointItemFullInfo: PointItemFullInfo): PointInfoViewModelFactory
    }
}