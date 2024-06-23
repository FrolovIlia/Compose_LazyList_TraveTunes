package io.travel_tunes.utils

import io.travel_tunes.BuildConfig

object BuildConfigUtils {
fun isDebugMode() = BuildConfig.DEBUG
    fun getBaseUrl() = ""
    fun getMerchantToken() = BuildConfig.MERCHANT_TOKEN
    fun getShopId() = BuildConfig.SHOP_ID
}
