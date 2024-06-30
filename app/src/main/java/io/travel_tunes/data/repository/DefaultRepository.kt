package io.travel_tunes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.travel_tunes.data.local.prefs.PreferenceManager
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.model.route.RouteInfoForView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

interface DefaultRepository {
    val uniqueIdFlow: Flow<String>
    suspend fun setUniqueId(uniqueId: String)
    val routePaidTagsSet: Flow<Set<String>>
    suspend fun updatePaymentsAndRoutesInfo(newPaymentsSet: Set<String>): Boolean
    suspend fun getPaymentVariantsForBuy(onResultReady: (List<PaymentVariant>) -> Unit)
    suspend fun setCurrentRouteTag(routeTag: String): Boolean

    val selectedRouteInfoForView: LiveData<RouteInfoForView?>
    fun setSelectedRouteInfoForView(routeInfoForView: RouteInfoForView)

    suspend fun updateRouteInfoAfterSuccessBuy()
}

@Singleton
class DefaultRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager
) : DefaultRepository {

    /**
     * для хранения инфы о выбранном маршруте
     */
    private val _selectedRouteInfoForView = MutableLiveData<RouteInfoForView>()
    override val selectedRouteInfoForView
        get() = _selectedRouteInfoForView

    override val uniqueIdFlow: Flow<String>
        get() = preferenceManager.uniqueIdFlow

    override suspend fun setUniqueId(uniqueId: String) {
        preferenceManager.setUniqueId(uniqueId)
    }

    override val routePaidTagsSet: Flow<Set<String>>
        get() = preferenceManager.routePaidTagsSet

    override suspend fun updatePaymentsAndRoutesInfo(newPaymentsSet: Set<String>): Boolean {
        return preferenceManager.updatePaymentsAndRoutesInfo(newPaymentsSet)
    }

    override suspend fun getPaymentVariantsForBuy(onResultReady: (List<PaymentVariant>) -> Unit) {
        preferenceManager.getPaymentVariantsForBuy(onResultReady)
    }

    override suspend fun setCurrentRouteTag(routeTag: String): Boolean {
        return preferenceManager.setCurrentRouteTag(routeTag)
    }

    override fun setSelectedRouteInfoForView(routeInfoForView: RouteInfoForView) {
        if (routeInfoForView != _selectedRouteInfoForView.value) {
            _selectedRouteInfoForView.postValue(routeInfoForView)
        }
    }

    override suspend fun updateRouteInfoAfterSuccessBuy() {
        val currentRoute = _selectedRouteInfoForView.value ?: return
        val paidTags = preferenceManager.routePaidTagsSet.first()
        if (currentRoute.getRouteTag() in paidTags && !currentRoute.isPaid()) {
            _selectedRouteInfoForView.value = (currentRoute.copy(isPaid = true))
        }
    }
}