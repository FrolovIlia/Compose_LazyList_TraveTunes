package io.travel_tunes.model.payments

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Currency

@Parcelize
data class PaymentVariant(
    @SerializedName("name") private val name: String,
    @SerializedName("labelStringRes") @StringRes private val labelStringRes: Int,
    @SerializedName("routeTagItems") private val routeTagItems: Set<String>,
    @SerializedName("amount") private val amount: Double,
    @SerializedName("currency") private val currency: Currency,
    @SerializedName("amountWithoutDiscount") private val amountWithoutDiscount: Double? = null // если заполнено, то отобразится
) : Parcelable {
    fun getName() = name.lowercase()
    fun getRouteTagSet() = routeTagItems
    fun getTextForUser(context: Context) = context.resources.getString(labelStringRes)
    fun getAmount() = amount
    fun getAmountWithCurrency() = "$amount ${currency.symbol}"
    fun getCurrency() = currency
    fun getAmountWithoutDiscount() = amountWithoutDiscount
}