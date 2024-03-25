package io.travel_tunes.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.travel_tunes.R
import io.travel_tunes.data.BuildConfig
import ru.yoomoney.sdk.kassa.payments.Checkout.createTokenizationResult
import ru.yoomoney.sdk.kassa.payments.Checkout.createTokenizeIntent
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.GooglePayParameters
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentParameters
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.SavePaymentMethod
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.TestParameters
import java.math.BigDecimal
import java.util.Currency
import java.util.Locale

class PayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_TOKENIZE) {
            when (resultCode) {
                Activity.RESULT_OK -> showToken(data)
                Activity.RESULT_CANCELED -> showError()
            }
        }
    }

    private fun initUi() {
        setContentView(R.layout.fragment_route_info)
        findViewById<Button>(R.id.tokenizeButton).setOnClickListener {
            onTokenizeButtonCLick()
        }
    }

    private fun onTokenizeButtonCLick() {
        val paymentMethodTypes = setOf(
            PaymentMethodType.GOOGLE_PAY,
            PaymentMethodType.BANK_CARD,
            PaymentMethodType.SBERBANK,
            PaymentMethodType.YOO_MONEY,
            PaymentMethodType.SBP,
        )
        val paymentParameters = PaymentParameters(
            amount = Amount(BigDecimal.valueOf(10.0), Currency.getInstance("RUB")),
            title = getString(R.string.main_product_name),
            subtitle = getString(R.string.main_product_description),
            clientApplicationKey = BuildConfig.MERCHANT_TOKEN,
            shopId = BuildConfig.SHOP_ID,
            savePaymentMethod = SavePaymentMethod.OFF,
            paymentMethodTypes = paymentMethodTypes,
            gatewayId = BuildConfig.GATEWAY_ID,
            customReturnUrl = getString(R.string.test_redirect_url),
            userPhoneNumber = getString(R.string.test_phone_number),
            googlePayParameters = GooglePayParameters(),
            authCenterClientId = BuildConfig.CLIENT_ID
        )

        val intent = createTokenizeIntent(this, paymentParameters, TestParameters(showLogs = true))
        startActivityForResult(intent, REQUEST_CODE_TOKENIZE)
    }

    private fun showToken(data: Intent?) {
        if (data != null) {
            val token = createTokenizationResult(data).paymentToken
            Toast.makeText(
                this,
                String.format(Locale.getDefault(), getString(R.string.tokenization_success), token),
                Toast.LENGTH_LONG
            ).show()
        } else {
            showError()
        }
    }

    private fun showError() {
        Toast.makeText(this, R.string.tokenization_canceled, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUEST_CODE_TOKENIZE = 1
    }
}