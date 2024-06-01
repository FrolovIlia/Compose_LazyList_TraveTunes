package io.travel_tunes.utils.prefs

import io.travel_tunes.utils.content.ProjectSetup

object PaymentsAndRoutesUtils {
    fun isCurrentRoutePaid(routeTag: String, paymentsFromPrefs: Set<String>): Boolean {
        if (paymentsFromPrefs.isEmpty()) return false
        val paidRoutes = getRouteTagListFromPayments(paymentsFromPrefs)
        return paidRoutes.contains(routeTag)
    }

    /**
     * вернет список тегов оплаченных маршрутов, по списку платежей
     */
    fun getRouteTagListFromPayments(paymentsFromPrefs: Set<String>): Set<String> {
        val result = mutableSetOf("")
        if (paymentsFromPrefs.isEmpty()) return emptySet()
        val paymentVariants = ProjectSetup.PAYMENT_VARIANTS
        paymentsFromPrefs.forEach { paymentName ->
            val currentPaymentVariant =
                paymentVariants.firstOrNull { it.getName().equals(paymentName, ignoreCase = true) }
                    ?: return@forEach
            result.addAll(currentPaymentVariant.getRouteTagSet())
        }
        return result
    }

    /**
     * вернет список доступных платежей по текущему списку платежей и текущему маршруту
     */
    fun getRouteTagListFromPayments(currentRouteTag: String,paymentsFromPrefs: Set<String>): Set<String> {
        val result = mutableSetOf("")
        if (paymentsFromPrefs.isEmpty()) return emptySet()
        val paymentVariants = ProjectSetup.PAYMENT_VARIANTS
        // FIXME: доработать метод
        return result
    }
}