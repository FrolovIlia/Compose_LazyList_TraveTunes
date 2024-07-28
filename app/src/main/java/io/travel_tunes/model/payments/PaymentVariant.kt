package io.travel_tunes.model.payments

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import java.util.Currency

@Parcelize
data class PaymentVariant(
    private val name: String,
    @StringRes private val labelStringRes: Int,
    private val routeTagItems: Set<String>,
    private val amount: Double,
    private val currency: Currency,
    private val amountWithoutDiscount: Double? = null // если заполнено, то отобразится
) : Parcelable {
    fun getName() = name.lowercase()
    fun getRouteTagSet() = routeTagItems
    fun getTextForUser(context: Context) = context.resources.getString(labelStringRes)
    fun getAmount() = amount
    fun getAmountWithCurrency() = "$amount ${currency.symbol}"
    fun getCurrency() = currency
    fun getAmountWithoutDiscount() = amountWithoutDiscount
}