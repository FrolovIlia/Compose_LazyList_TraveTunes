package io.travel_tunes.utils.payment

import android.content.Context
import android.content.Intent
import io.travel_tunes.R
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.utils.BuildConfigUtils
import ru.yoomoney.sdk.kassa.payments.Checkout
import ru.yoomoney.sdk.kassa.payments.TokenizationResult
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentParameters
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.SavePaymentMethod
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.UiParameters
import ru.yoomoney.sdk.kassa.payments.ui.color.ColorScheme
import java.math.BigDecimal
import java.util.Locale
import java.util.UUID

object UkassaHelper {
    fun generateIntentForTokenize(context: Context, paymentVariant: PaymentVariant): Intent {
        val paymentParameters = PaymentParameters(
            amount = Amount(
                BigDecimal.valueOf(paymentVariant.getAmount()),
                paymentVariant.getCurrency()
            ),
            title = paymentVariant.getTextForUser(context),
            subtitle = "",
            clientApplicationKey = BuildConfigUtils.getMerchantToken(),
            shopId = BuildConfigUtils.getShopId(),
            savePaymentMethod = SavePaymentMethod.OFF,
            paymentMethodTypes = setOf(PaymentMethodType.BANK_CARD, PaymentMethodType.SBP)
        )
        return Checkout.createTokenizeIntent(
            context,
            paymentParameters = paymentParameters,
            uiParameters = generateUiParameters(context)
        )
    }

    fun getTokenizationResultFromResult(data: Intent): TokenizationResult {
        return Checkout.createTokenizationResult(data)
    }

    fun generateIdempotenceKey(): String {
        val uuid = UUID.randomUUID().toString()
        return uuid.uppercase(Locale.CANADA)
    }

    fun getConfirmationIntent(context: Context, confirmationIntentData: ConfirmationIntentData): Intent {
        return Checkout.createConfirmationIntent(
            context,
            confirmationIntentData.getConfirmationUrl(),
            confirmationIntentData.getPaymentMethodType(),
            colorScheme = generateUiParameters(context).colorScheme
        )
    }

    private fun generateUiParameters(context: Context) = UiParameters(
        showLogo = false,
        colorScheme = ColorScheme(context.resources.getColor(R.color.main_blue))
    )
}

data class ConfirmationIntentData(
    private val confirmationUrl: String,
    private val paymentMethodType: PaymentMethodType
) {
    fun getConfirmationUrl() = confirmationUrl
    fun getPaymentMethodType() = paymentMethodType
}