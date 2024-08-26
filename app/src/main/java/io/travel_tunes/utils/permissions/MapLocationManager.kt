package io.travel_tunes.utils.permissions

import android.annotation.SuppressLint
import android.app.Activity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import io.travel_tunes.utils.extencions.createLocationRequest
import io.travel_tunes.utils.extencions.isLocationPermissionFineGranted
import io.travel_tunes.utils.map.LatLngNew
import timber.log.Timber

class MapLocationManager(
    private val activity: Activity?,

    /**
     * для первого обновления локации юзера - надо еще отцентрировать карту на местоположении юзера
     */
    private val firstLocationResult: (LatLngNew?) -> Unit,
    /**
     * для обновления локации юзера
     */
    private val anotherLocationResult: (LatLngNew?) -> Unit,
) {
    private var isNextLocationNeedCenterMap: Boolean = false
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest = createLocationRequest()
    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                if (isNextLocationNeedCenterMap) {
                    isNextLocationNeedCenterMap = false
                    firstLocationResult.invoke(LatLngNew(location.latitude, location.longitude))
                } else {
                    anotherLocationResult.invoke(LatLngNew(location.latitude, location.longitude))
                }
                break
            }
        }
    }
    private var requestingLocationUpdates: Boolean = false

    fun isRequestingLocationUpdates(): Boolean = requestingLocationUpdates
    fun setRequestingLocationUpdates(isRequesting: Boolean) {
        requestingLocationUpdates = isRequesting
    }

    fun initLocation() {
        if (activity == null) return
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation(
        activity: Activity?
    ) {
        if (activity != null && activity.isLocationPermissionFineGranted()) {
            fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                if (location == null) {
                    isNextLocationNeedCenterMap = true
                    initLocation()
                } else {
                    firstLocationResult.invoke(LatLngNew(location.latitude, location.longitude))
                }
            }
        }
    }

    fun onMapFragmentResume(activity: Activity?) {
        if (!requestingLocationUpdates) {
            startLocationUpdates(activity)
        }
    }

    fun onMapFragmentPause() {
        stopLocationUpdates()
    }

    private fun startLocationUpdates(activity: Activity?) {
        if (activity?.isLocationPermissionFineGranted() == true) {
            try {
                fusedLocationClient?.requestLocationUpdates(
                    locationRequest,
                    locationCallback, null
                )
                requestingLocationUpdates = true
            } catch (ex: SecurityException) {
                Timber.e(ex)
            }
        }
    }

    private fun stopLocationUpdates() {
        if (requestingLocationUpdates) {
            locationCallback.let { fusedLocationClient?.removeLocationUpdates(it) }
            requestingLocationUpdates = false
        }
    }
}