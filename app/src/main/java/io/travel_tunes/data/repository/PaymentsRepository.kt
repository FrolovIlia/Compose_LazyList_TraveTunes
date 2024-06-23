package io.travel_tunes.data.repository

import io.travel_tunes.data.local.prefs.PreferenceManager
import io.travel_tunes.data.remote.PaymentsApi
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.model.remote.AmountRequest
import io.travel_tunes.model.remote.MetadataRequest
import io.travel_tunes.model.remote.PaymentsRequest
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface PaymentsRepository {
    suspend fun sendPayments(paymentVariant: PaymentVariant, token: String)
    suspend fun getPaymentById(paymentId: String): String
    suspend fun getPayments(): List<String>
}

//@Singleton
class PaymentsRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val paymentsApi: PaymentsApi
) : PaymentsRepository {
    /**
     * кешируем, чтобы не дергать каждый раз
     */
    private var uniqueIdSaved: String? = null
    override suspend fun sendPayments(paymentVariant: PaymentVariant, token: String) {
        val uniqueId = getSavedUniqueId()
        val paymentsRequest = PaymentsRequest(
            amount = AmountRequest(value = paymentVariant.getAmount().toString(), currency = paymentVariant.getCurrency().currencyCode),
            metadata = MetadataRequest(
                uniqueId = uniqueId,
                paymentVariant = paymentVariant.getName()
            ),
            paymentToken = token
        )
        paymentsApi.sendPayments(paymentsRequest)
    }

    override suspend fun getPaymentById(paymentId: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getPayments(): List<String> {
        TODO("Not yet implemented")
    }

    private suspend fun getSavedUniqueId(): String {
        return if (uniqueIdSaved.isNullOrBlank()) {
            val result = preferenceManager.uniqueIdFlow.first()
            uniqueIdSaved = result
            result
        } else {
            uniqueIdSaved!!
        }
    }
}