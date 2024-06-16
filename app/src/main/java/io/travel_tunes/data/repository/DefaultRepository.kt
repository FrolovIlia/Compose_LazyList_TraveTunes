package io.travel_tunes.data.repository

import io.travel_tunes.data.local.prefs.PreferenceManager
import io.travel_tunes.model.payments.PaymentVariant
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface DefaultRepository {
    val uniqueIdFlow: Flow<String>
    suspend fun setUniqueId(uniqueId: String)
    val routePaidTagsSet: Flow<Set<String>>
    suspend fun updatePaymentsAndRoutesInfo(newPaymentsSet: Set<String>): Boolean
    suspend fun getPaymentVariantsForBuy(onResultReady: (List<PaymentVariant>) -> Unit)
    suspend fun setCurrentRouteTag(routeTag: String): Boolean
}

@Singleton
class DefaultRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager
) : DefaultRepository {
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
}