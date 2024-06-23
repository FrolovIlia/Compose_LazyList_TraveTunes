package io.travel_tunes.data.repository

import android.content.Context
import io.travel_tunes.data.local.prefs.PreferenceManager
import io.travel_tunes.data.remote.PaymentsApi
import io.travel_tunes.data.remote.Resource
import io.travel_tunes.data.remote.isSuccess
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.model.remote.AmountRequest
import io.travel_tunes.model.remote.MetadataRequest
import io.travel_tunes.model.remote.PaymentsRequest
import io.travel_tunes.model.remote.PaymentsResponse
import io.travel_tunes.model.remote.PaymentsResponsesList
import io.travel_tunes.utils.base.BaseRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface PaymentsRepository {
    suspend fun sendPayments(
        paymentVariant: PaymentVariant,
        token: String
    ): Resource<PaymentsResponse>

    suspend fun getPaymentById(paymentId: String): Resource<PaymentsResponse>
    suspend fun getPayments(): Resource<PaymentsResponsesList>
}

//@Singleton
class PaymentsRepositoryImpl @Inject constructor(
    context: Context,
    private val preferenceManager: PreferenceManager,
    private val paymentsApi: PaymentsApi
) : PaymentsRepository, BaseRepository(context) {
    /**
     * кешируем, чтобы не дергать каждый раз
     */
    private var uniqueIdSaved: String? = null
    override suspend fun sendPayments(
        paymentVariant: PaymentVariant,
        token: String
    ): Resource<PaymentsResponse> {
        val uniqueId = getSavedUniqueId()
        val paymentsRequest = PaymentsRequest(
            amount = AmountRequest(
                value = paymentVariant.getAmount().toString(),
                currency = paymentVariant.getCurrency().currencyCode
            ),
            metadata = MetadataRequest(
                uniqueId = uniqueId,
                paymentVariant = paymentVariant.getName()
            ),
            paymentToken = token
        )
        val result = safeApiCall {
            paymentsApi.sendPayments(paymentsRequest)
        }
        // TODO: возможно тут стоит запрашивать инфу о всех покупках юзера?
        if (result.isSuccess() && (result as Resource.Success).value.isStatusSucceeded()) {
            preferenceManager.updatePaymentsAndRoutesInfo(newPaymentsSet = setOf(paymentVariant.getName()))
        }
        return result
    }

    override suspend fun getPaymentById(paymentId: String): Resource<PaymentsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getPayments(): Resource<PaymentsResponsesList> {
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