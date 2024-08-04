# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# profiling SDK wants OkHttp to be available like this
-keep class okhttp3.** { *; }
-keep class okio.** { *; }
-keep class org.threeten.bp.** { *; }
-keep class ru.yoomoney.sdk.auth.model.** { *; }
-keep class ru.yoomoney.sdk.auth.ProcessType** { *; }
-keep class ru.yoomoney.sdk.kassa.payments.Checkout { *; }
-keep class ru.yoomoney.sdk.kassa.payments.ui.** { *; }
-keep class androidx.lifecycle.YooKassaKeyedFactory { *; }
-keep class androidx.lifecycle.ViewModelKeyedFactory { *; }
-keep class androidx.lifecycle.YooKassaViewModelProvider { *; }
-keep class ru.yoomoney.sdk.kassa.payments.utils.WebTrustManagerImpl { *; }
-keep class ru.yoomoney.sdk.kassa.payments.utils.WebTrustManager { *; }

-keeppackagenames ru.yoomoney.sdk.kassa.payments.**
-keeppackagenames ru.yoomoney.sdk.kassa.payments
-keeppackagenames com.group_ib.sdk

-keep class ru.yoomoney.sdk.yooprofiler.* { *; }

-dontwarn javax.annotation.Nullable
-dontwarn org.conscrypt.OpenSSLProvider
-dontwarn org.conscrypt.Conscrypt
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn java.lang.invoke.StringConcatFactory

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Keep API models for Jackson
-keep class ru.yoomoney.sdk.kassa.payments.api.model.** { *; }
-keep class ru.yoomoney.sdk.kassa.payments.model.Config { *; }
-keep class ru.yoomoney.sdk.kassa.payments.model.ConfigPaymentOption { *; }
-keep class ru.yoomoney.sdk.kassa.payments.model.SavePaymentMethodOptionTexts { *; }
#-keep class ru.yoomoney.sdk.kassa.payments.model.SberPayParticipants { *; }
-keep class retrofit2.converter.jackson.ResultJacksonResponseBodyConverter { *; }
-keep class ru.yoomoney.sdk.kassa.payments.api.YooKassaJacksonConverterFactory { *; }
-keep class ru.yoomoney.sdk.kassa.payments.api.JacksonBaseObjectMapperKt { *; }
#-keep class ru.yoomoney.sdk.kassa.payments.utils.compose.BottomSheetCornersKt { *; }
#-keep class ru.yoomoney.sdk.kassa.payments.utils.compose.OnLifecycleEventKt { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class ru.yoomoney.sdk.auth.account.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

-keep,allowobfuscation,allowshrinking class kotlin.Result
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# This is generated automatically by the Android Gradle plugin.
-dontwarn com.vk.api.sdk.VK
-dontwarn com.vk.auth.api.models.AuthResult
-dontwarn com.vk.auth.main.SignUpData
-dontwarn com.vk.auth.main.SilentAuthSource
-dontwarn com.vk.auth.main.VkClientAuthCallback$DefaultImpls
-dontwarn com.vk.auth.main.VkClientAuthCallback
-dontwarn com.vk.auth.main.VkClientAuthLib
-dontwarn com.vk.auth.main.VkClientAuthLibConfig$AuthModelData
-dontwarn com.vk.auth.main.VkClientLibverifyInfo$Companion
-dontwarn com.vk.auth.main.VkClientLibverifyInfo
-dontwarn com.vk.auth.main.VkClientUiInfo
-dontwarn com.vk.auth.main.VkFastLoginModifiedUser
-dontwarn com.vk.auth.main.VkSilentTokenExchanger$Result$Success
-dontwarn com.vk.auth.main.VkSilentTokenExchanger$Result
-dontwarn com.vk.auth.main.VkSilentTokenExchanger
-dontwarn com.vk.auth.migration.VkMigrationResult
-dontwarn com.vk.auth.oauth.VkOAuthConnectionResult$Error
-dontwarn com.vk.auth.oauth.VkOAuthConnectionResult
-dontwarn com.vk.auth.oauth.VkOAuthService
-dontwarn com.vk.auth.oauth.model.AdditionalOauthAuthResult
-dontwarn com.vk.auth.ui.fastloginbutton.VkFastLoginButton
-dontwarn com.vk.auth.validation.VkPhoneValidationCompleteResult
-dontwarn com.vk.auth.validation.VkPhoneValidationErrorReason
-dontwarn com.vk.silentauth.SilentAuthInfo
-dontwarn com.vk.superapp.SuperappKit
-dontwarn com.vk.superapp.SuperappKitConfig$Builder
-dontwarn com.vk.superapp.SuperappKitConfig
-dontwarn com.vk.superapp.bridges.LogoutReason
-dontwarn com.vk.superapp.core.SuperappConfig$AppInfo
-dontwarn org.slf4j.impl.StaticLoggerBinder

-dontwarn java.beans.ConstructorProperties
-dontwarn java.beans.Transient