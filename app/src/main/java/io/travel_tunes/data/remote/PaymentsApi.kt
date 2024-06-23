package io.travel_tunes.data.remote

import io.travel_tunes.model.remote.PaymentsRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PaymentsApi {
    @GET("v3/payments/{payment_id}")
    suspend fun getPaymentById(
        @Path("payment_id") paymentId: String
    ): String

    @GET("v3/payments")
    suspend fun getPayments(
        @Query("status") status: String = "succeeded"
    ): String

    @POST("v3/payments")
    suspend fun sendPayments(
        @Body paymentsRequest: PaymentsRequest
    ): String
}