package io.travel_tunes.utils.permissions

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.location.LocationManager
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.OnFailureListener
import timber.log.Timber
import java.util.concurrent.TimeUnit

class GpsUtils(private val context: Context) {
    private var settingsClient: SettingsClient? = null
    private var locationSettingsRequest: LocationSettingsRequest? = null
    private var locationManager: LocationManager? = null

    init {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        settingsClient = LocationServices.getSettingsClient(context)
        LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            TimeUnit.SECONDS.toMillis(10)
        ).build().let {
            LocationSettingsRequest.Builder().addLocationRequest(it).apply {
                locationSettingsRequest = build()
                setAlwaysShow(true)
            }
        }
    }

    fun turnGPSOn(
        onGpsListener: ((Boolean) -> Unit)? = null,
        onRaeExceptionListener: ((PendingIntent) -> Unit)? = null
    ) {
        if (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
            onGpsListener?.invoke(true)
        } else {
            settingsClient?.apply {
                if (locationSettingsRequest != null) {
                    checkLocationSettings(locationSettingsRequest!!)
                        .addOnSuccessListener(
                            context as Activity
                        ) {
                            //  GPS is already enable, callback GPS status through listener
                            onGpsListener?.invoke(true)
                        }
                        .addOnFailureListener(context, OnFailureListener { e: Exception ->
                            when ((e as ApiException).statusCode) {
                                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    val rae = e as ResolvableApiException
                                    if (onRaeExceptionListener != null) {
                                        onRaeExceptionListener.invoke(rae.resolution)
                                    } else {
                                        rae.startResolutionForResult(context, 666)
                                    }
                                } catch (sie: SendIntentException) {
                                    Timber.e("PendingIntent unable to execute request.")
                                }

                                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                                    val errorMessage =
                                        "Location settings are inadequate, and cannot be " +
                                                "fixed here. Fix in Settings."
                                    Timber.e(errorMessage)
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                }
            }
        }
    }
}