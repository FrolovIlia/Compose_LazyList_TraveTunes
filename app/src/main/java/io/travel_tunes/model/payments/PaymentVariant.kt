package io.travel_tunes.model.payments

data class PaymentVariant(
    private val name: String,
    private val routeTagItems: Set<String>,
    private val amount: String
) {
    fun getName() = name.lowercase()
    fun getRouteTagSet() = routeTagItems
    fun getAmount() = amount
}