package io.travel_tunes.utils.content

import io.travel_tunes.model.payments.PaymentVariant

object ProjectSetup {
    const val TABLE_NAME = "ivanovo_app"

    val ROUTES_LIST = listOf(RouteIvanovo1())
    val PAYMENT_VARIANTS = setOf(
        PaymentVariant(
            name = "ivanovo_1_item_key",
            routeTagItems = setOf(RouteIvanovo1().getRouteTag()),
            amount = "499₽"
        )
    )
}