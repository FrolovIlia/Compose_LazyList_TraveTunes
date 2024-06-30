package io.travel_tunes.utils.payment

import android.content.Context
import android.content.Intent
import io.travel_tunes.R
import io.travel_tunes.data.remote.Resource
import io.travel_tunes.model.payments.PaymentVariant
import io.travel_tunes.utils.BuildConfigUtils
import io.travel_tunes.utils.CrashlyticsUtils
import io.travel_tunes.utils.MessageProgress
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

    fun getConfirmationIntent(
        context: Context,
        confirmationIntentData: ConfirmationIntentData
    ): Intent {
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

    /**
     * https://yookassa.ru/developers/payment-acceptance/after-the-payment/declined-payments
     * тексты ошибок для отмены платежа
     */
    fun getMessageForCancelReason(cancelReason: String): MessageProgress {
        val errorStringRes = when (cancelReason) {
            "3d_secure_failed" -> R.string.cancel_error_3d_secure_failed
            "call_issuer" -> R.string.cancel_error_call_issuer
            "card_expired" -> R.string.cancel_error_card_expired
            "country_forbidden" -> R.string.cancel_error_country_forbidden
            "fraud_suspected" -> R.string.cancel_error_fraud_suspected
            "expired_on_confirmation" -> R.string.cancel_error_expired_on_confirmation
            "insufficient_funds" -> R.string.cancel_error_insufficient_funds
            "internal_timeout" -> R.string.cancel_error_internal_timeout
            "invalid_card_number" -> R.string.cancel_error_invalid_card_number
            "invalid_csc" -> R.string.cancel_error_invalid_csc
            "issuer_unavailable" -> R.string.cancel_error_issuer_unavailable
            "payment_method_limit_exceeded" -> R.string.cancel_error_payment_method_limit_exceeded
            "payment_method_restricted" -> R.string.cancel_error_payment_method_restricted
            else -> R.string.cancel_error_undefined
        }
        return MessageProgress(textMessage = null, resStringInt = errorStringRes)
    }

    /**
     * если ошибка с выключенным инетом, то покажем сообщение
     * - failure_error_is_network_error
     * если ошибка относится к проходящим со временем,
     * например "Слишком много запросов" или "Внутренняя ошибка сервера Ukassa" то покажем сообщение
     * - failure_error_temporary_problem
     * если ошибка с запросами/мерчантами, то пусть сообщат нам:
     * - failure_error_undefined_need_fix
     * иначе покажем неизвестную ошибку
     * - cancel_error_undefined
     * https://yookassa.ru/developers/using-api/response-handling/response-format
     */
    fun getMessageForFailureError(failure: Resource.Failure): MessageProgress {
        CrashlyticsUtils.sendThrowableNonFatal(e = failure.getThrowable() ?: Throwable("getMessageForFailureError = failure = $failure"))
        val knownErrorCodes = listOf("invalid_request", "invalid_credentials", "forbidden", "not_found", )
        val knownErrorCodesTemporary = listOf("too_many_requests", "internal_server_error")
        return when {
            failure.isNetworkError() -> {
                MessageProgress(
                    resStringInt = R.string.failure_error_is_network_error,
                    textMessage = null
                )
            }

            else -> {
                val failureCode = failure.getFailureDetails()?.getCode()
                when {
                    !failureCode.isNullOrBlank() && failureCode.lowercase() in knownErrorCodesTemporary -> {
                        // ошибка относится к временным
                        MessageProgress(
                            resStringInt = R.string.failure_error_temporary_problem,
                            textMessage = null
                        )
                    }
                    !failureCode.isNullOrBlank() && failureCode.lowercase() in knownErrorCodes -> {
                        // ошибка не должна была возникнуть, пусть сообщат нам
                        MessageProgress(
                            resStringInt = R.string.failure_error_undefined_need_fix,
                            textMessage = null
                        )
                    }
                     else -> {
                         MessageProgress(
                             resStringInt = R.string.cancel_error_undefined,
                             textMessage = null
                         )
                     }
                }
            }
        }
    }
}

data class ConfirmationIntentData(
    private val confirmationUrl: String,
    private val paymentMethodType: PaymentMethodType
) {
    fun getConfirmationUrl() = confirmationUrl
    fun getPaymentMethodType() = paymentMethodType
}