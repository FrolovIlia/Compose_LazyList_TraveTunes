package io.travel_tunes.utils

import io.travel_tunes.BuildConfig

object BuildConfigUtils {
    fun getVersionName() = BuildConfig.VERSION_NAME
    fun isDebugMode() = BuildConfig.DEBUG
    fun getBaseUrl() = "https://api.yookassa.ru/"
    fun getMerchantToken() = BuildConfig.MERCHANT_TOKEN
    fun getSecretKey() = BuildConfig.SECRET_KEY
    fun getShopId() = BuildConfig.SHOP_ID
    fun getApplicationId() = BuildConfig.APPLICATION_ID
}