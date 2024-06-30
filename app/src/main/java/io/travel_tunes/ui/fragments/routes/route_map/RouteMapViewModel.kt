package io.travel_tunes.ui.fragments.routes.route_map

import androidx.annotation.RawRes
import androidx.lifecycle.MutableLiveData
import io.travel_tunes.data.repository.DefaultRepository
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.model.route.PointItemFullInfo
import io.travel_tunes.model.route.PointItemInfo
import io.travel_tunes.model.route.RouteItemInfo
import io.travel_tunes.utils.base.BaseViewModel
import io.travel_tunes.utils.base.BaseViewModelFactory
import io.travel_tunes.utils.extencions.SingleLiveEvent
import javax.inject.Inject

class RouteMapViewModel(
    private val defaultRepository: DefaultRepository
) : BaseViewModel() {

    val routeTitle = MutableLiveData<String>()

    val routeItemInfo = MutableLiveData<RouteItemInfo>()

    val selectedRouteSealedInfo = defaultRepository.selectedRouteInfoForView

    private val _openPaymentsScreen = SingleLiveEvent<List<PaymentVariant>?>()
    val openPaymentsScreen
        get() = _openPaymentsScreen

    private val _hidePointInfoBottomFragmentEvent = SingleLiveEvent<Boolean?>()
    val hidePointInfoBottomFragmentEvent
        get() = _hidePointInfoBottomFragmentEvent

    private val _openPointInfoScreen = SingleLiveEvent<PointItemFullInfo?>()
    val openPointInfoScreen
        get() = _openPointInfoScreen

    @RawRes
    val routeKmlInfo = MutableLiveData<Int?>()

    init {
        initRouteInfo()
    }

    private fun initRouteInfo() {
        val routeInfoForView = defaultRepository.selectedRouteInfoForView.value ?: return
        val routeInfo = routeInfoForView.getRouteItemInfo()
        val updatedRouteInfo =
            routeInfo.copy(points = routeInfo.getPoints(isPaid = routeInfoForView.isPaid()))
        val routeKmlRes = routeInfoForView.getRouteKmlRes()
        routeItemInfo.value = updatedRouteInfo
        routeTitle.value = updatedRouteInfo.getTitle()
        routeKmlInfo.value = routeKmlRes
    }

    fun onMapReady() {
        routeItemInfo.value = routeItemInfo.value
        routeKmlInfo.value = routeKmlInfo.value
    }

    fun handleOnMarkerPointClick(pointItemInfo: PointItemInfo) {
        when {
            !pointItemInfo.isEnabled() -> {
                preparePaymentVariantsAndStartEvent()
            }

            !pointItemInfo.isSelected() -> {
                val currentRouteInfo = routeItemInfo.value
                val updatedPoints = currentRouteInfo?.getPoints().orEmpty().map { point ->
                    point.copy(isSelected = pointItemInfo.getId() == point.getId())
                }
                routeItemInfo.value = currentRouteInfo?.copy(points = updatedPoints)
                val pointItemFullInfo = getPointItemFullInfo(pointItemInfo.getId())
                if (_openPointInfoScreen.value != pointItemFullInfo) {
                    _openPointInfoScreen.postValue(pointItemFullInfo)
                }
            }
        }
    }

    fun handleOnMapClick() {
        clearSelectedPoint()
    }

    private fun getPointItemFullInfo(id: String) =
        selectedRouteSealedInfo.value?.getPointItemFullInfo(id)

    private fun clearSelectedPoint() {
        val currentRouteInfo = routeItemInfo.value
        val updatedPoints = currentRouteInfo?.getPoints().orEmpty().map { point ->
            point.copy(isSelected = false)
        }
        routeItemInfo.value = currentRouteInfo?.copy(points = updatedPoints)

        // скрыть плашку если есть
        startHidePointInfoBottomFragmentEvent()
    }

    private fun startHidePointInfoBottomFragmentEvent() {
        if (_hidePointInfoBottomFragmentEvent.value != true) {
            _hidePointInfoBottomFragmentEvent.postValue(true)
        }
    }

    fun bottomSheetIsHidden() {
        handleOnMapClick()
    }

    fun clearOpenPointInfoScreen() {
        _openPointInfoScreen.call()
    }

    fun clearOpenPaymentsScreen() {
        _openPaymentsScreen.call()
    }

    fun clearHidePointInfoBottomFragmentEvent() {
        _hidePointInfoBottomFragmentEvent.call()
    }

    private fun preparePaymentVariantsAndStartEvent() {
        launchAtViewModelScope {
            defaultRepository.getPaymentVariantsForBuy { result ->
                if (result.isEmpty()) return@getPaymentVariantsForBuy
                if (_openPaymentsScreen.value != result) {
                    _openPaymentsScreen.postValue(result)
                }
                clearSelectedPoint()
            }
        }
    }

    fun updateRouteInfoAfterSuccessBuy() {
        initRouteInfo()
    }
}

class RouteMapViewModelFactory @Inject constructor(
    private val defaultRepository: DefaultRepository
) : BaseViewModelFactory<RouteMapViewModel>() {

    override fun getViewModel(): RouteMapViewModel {
        return RouteMapViewModel(defaultRepository)
    }
}