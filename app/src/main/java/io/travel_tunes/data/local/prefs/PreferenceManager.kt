package io.travel_tunes.data.local.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.utils.extencions.dataStore
import io.travel_tunes.utils.extencions.getValueFlow
import io.travel_tunes.utils.extencions.setValue
import io.travel_tunes.utils.prefs.PaymentsAndRoutesUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceManager @Inject constructor(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val KEY_AUTH = stringPreferencesKey("key_auth")
        private val UNIQUE_ID = stringPreferencesKey("unique_id")

        private val PAYMENTS_LIST = stringSetPreferencesKey("payments_list")
        private val ROUTE_PAID_TAGS_LIST = stringSetPreferencesKey("route_paid_tags_list")
        private val ROUTE_CURRENT_TAG = stringPreferencesKey("route_current_tag")
    }

    val authToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    val uniqueIdFlow = dataStore.getValueFlow(UNIQUE_ID, "")

    suspend fun setUniqueId(uniqueId: String) {
        dataStore.setValue(UNIQUE_ID, uniqueId)
    }

    val routePaidTagsSet = dataStore.getValueFlow(ROUTE_PAID_TAGS_LIST, emptySet())

    suspend fun updatePaymentsAndRoutesInfo(newPaymentsSet: Set<String>): Boolean {
        dataStore.edit { preferences ->
            val oldPayments = preferences[PAYMENTS_LIST] ?: emptySet()
            // считаем, что меньше стать не может, лишь больше
            val diff = newPaymentsSet.minus(oldPayments)

            if (diff.isNotEmpty()) {
                val resultPayments = oldPayments.plus(diff)
                val resultRoutesPaid =
                    PaymentsAndRoutesUtils.getRouteTagListFromPayments(resultPayments)
                preferences[PAYMENTS_LIST] = resultPayments
                preferences[ROUTE_PAID_TAGS_LIST] = resultRoutesPaid
            }
        }
        return true
    }

    suspend fun getPaymentVariantsForBuy(onResultReady: (List<PaymentVariant>) -> Unit) {
        dataStore.edit { preferences ->
            val currentRouteTag = preferences[ROUTE_CURRENT_TAG]
            if (currentRouteTag.isNullOrBlank()) {
                onResultReady.invoke(emptyList())
                return@edit
            } else {
                val result = PaymentsAndRoutesUtils.getPaymentVariantsForBuy(
                    currentRouteTag,
                    alreadyPaidRouteTags = preferences[ROUTE_PAID_TAGS_LIST] ?: emptySet()
                )
                onResultReady.invoke(result)
            }
        }
    }

    suspend fun setCurrentRouteTag(routeTag: String): Boolean {
        return dataStore.setValue(ROUTE_CURRENT_TAG, routeTag)
    }
}