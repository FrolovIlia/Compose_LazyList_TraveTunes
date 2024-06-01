package io.travel_tunes.utils.content

import io.travel_tunes.model.payments.PaymentVariant

object ProjectSetup {
    const val TABLE_NAME = "kazan_app"

    val ROUTES_LIST = listOf(RouteKazan1())

    val PAYMENT_VARIANTS = setOf(
        PaymentVariant(
            name = "kazan_1_item_key",
            routeTagItems = setOf(RouteKazan1().getRouteTag()),
            amount = "499₽"
        )
    )

    /**
     * как пример для 2 платежей
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
//            name = "kazan_1_2_items_key",
//            routeTagItems = setOf("kazan_1", "kazan_2"),
//            amount = "899₽"
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