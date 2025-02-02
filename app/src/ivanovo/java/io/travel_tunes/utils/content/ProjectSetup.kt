package io.travel_tunes.utils.content

import io.travel_tunes.R
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.utils.Payments
import java.util.Currency

object ProjectSetup {
    const val TABLE_NAME = "ivanovo_app"

    val ROUTES_LIST = listOf(RouteIvanovo1())
    val PAYMENT_VARIANTS = setOf(
        PaymentVariant(
            name = "ivanovo_1_item_key",
            labelStringRes = R.string.payment_variant_1_label,
            routeTagItems = setOf(RouteIvanovo1().getRouteTag()),
            amount = 349.0,
            currency = Currency.getInstance(Payments.CURRENCY_RUB)
        )
    )
}