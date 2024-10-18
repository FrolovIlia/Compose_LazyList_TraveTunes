package io.travel_tunes.utils.content

import io.travel_tunes.R
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.utils.Payments
import java.util.Currency

object ProjectSetup {
    const val TABLE_NAME = "suzdal_app"

    val ROUTES_LIST = listOf(RouteSuzdal1(), RouteSuzdal2())

//    val PAYMENT_VARIANTS = setOf(
//        PaymentVariant(
//            name = "suzdal_1_item_key",
//            labelStringRes = R.string.payment_variant_1_label,
//            routeTagItems = setOf(RouteSuzdal1().getRouteTag()),
//            amount = 499.0,
//            currency = Currency.getInstance(Payments.CURRENCY_RUB)
//        )
//    )


    val PAYMENT_VARIANTS = setOf(
        PaymentVariant(
            name = "suzdal_1_item_key",
            labelStringRes = R.string.payment_variant_1_label,
            routeTagItems = setOf(RouteSuzdal1().getRouteTag()),
            amount = 499.0,
            currency = Currency.getInstance(Payments.CURRENCY_RUB)
        ),
        PaymentVariant(
            name = "suzdal_2_item_key",
            labelStringRes = R.string.payment_variant_2_label,
            routeTagItems = setOf(RouteSuzdal2().getRouteTag()),
            amount = 399.0,
            currency = Currency.getInstance(Payments.CURRENCY_RUB)
        ),
        PaymentVariant(
            name = "suzdal_3_item_key",
            labelStringRes = R.string.payment_variant_3_label,
            routeTagItems = setOf(RouteSuzdal1().getRouteTag(), RouteSuzdal2().getRouteTag()),
            amount = 699.0,
            currency = Currency.getInstance(Payments.CURRENCY_RUB)
        )
    )


    /**
     * как пример для 2 платежей
     */
//        val PAYMENT_VARIANTS = setOf(
//        PaymentVariant(
//            name = R.string.payment_variant_1_label.toString(),
//            routeTagItems = setOf(RouteSuzdal1().getRouteTag()),
//            amount = 499.0,
//            currency = Currency.getInstance(Payments.CURRENCY_RUB)
//        ),
//        PaymentVariant(
//            name = R.string.payment_variant_2_label.toString(),
//            routeTagItems = setOf(RouteSuzdal2().getRouteTag()),
//            amount = 399.0,
//            currency = Currency.getInstance(Payments.CURRENCY_RUB)
//        ),
//        PaymentVariant(
//            name = R.string.payment_variant_3_label,
//            routeTagItems = setOf("kazan_1", "kazan_2"),
//            amount = 699.0,
//            currency = Currency.getInstance(Payments.CURRENCY_RUB)
//        )
//    )

    /**
     * как пример для 3 платежей
     */
//    val PAYMENT_VARIANTS = setOf(
//        PaymentVariant(
//            name = "kazan_1_item_key",
//            routeTagItems = setOf("kazan_1"),
//            amount = "499₽"
//        ),
//        PaymentVariant(
//            name = "kazan_2_item_key",
//            routeTagItems = setOf("kazan_2"),
//            amount = "499₽"
//        ),
//        PaymentVariant(
//            name = "kazan_3_item_key",
//            routeTagItems = setOf("kazan_3"),
//            amount = "499₽"
//        ),
//        PaymentVariant(
//            name = "kazan_1_2_items_key",
//            routeTagItems = setOf("kazan_1", "kazan_2"),
//            amount = "899₽"
//        ),
//        PaymentVariant(
//            name = "kazan_1_3_items_key",
//            routeTagItems = setOf("kazan_1", "kazan_3"),
//            amount = "899₽"
//        ),
//        PaymentVariant(
//            name = "kazan_2_3_items_key",
//            routeTagItems = setOf("kazan_2", "kazan_3"),
//            amount = "899₽"
//        ),
//        PaymentVariant(
//            name = "kazan_1_2_3_items_key",
//            routeTagItems = setOf("kazan_1", "kazan_2", "kazan_3"),
//            amount = "1199₽"
//        )
//    )
}