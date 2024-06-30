package io.travel_tunes.utils.payment

import android.content.Context
import android.content.Intent
import io.travel_tunes.R
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.utils.BuildConfigUtils
import ru.yoomoney.sdk.kassa.payments.Checkout
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
        // FIXME: поддержка 3ds? добавить customReturnUrl??
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
        val uiParameters = UiParameters(
            showLogo = false,
            colorScheme = ColorScheme(context.resources.getColor(R.color.main_blue))
        )
        return Checkout.createTokenizeIntent(
            context,
            paymentParameters = paymentParameters,
//            testParameters = testParameters,
            uiParameters = uiParameters
        )
    }

    fun getTokenFromResult(data: Intent): String {
        val tokenizationResult = Checkout.createTokenizationResult(data)
        //        val paymentMethod = tokenizationResult.paymentMethodType
        return tokenizationResult.paymentToken
    }

    fun generateIdempotenceKey(): String {
        val uuid = UUID.randomUUID().toString()
        return uuid.uppercase(Locale.CANADA)
    }
}