package io.travel_tunes.utils.permissions

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import io.travel_tunes.utils.BuildConfigUtils

object PermissionUtils {
    @JvmInline
    value class Permission(val result: ActivityResultLauncher<Array<String>>)

    private fun getPermissionState(
        activity: Activity?,
        result: MutableMap<String, Boolean>
    ): PermissionState {
        val deniedList: List<String> = result.filter {
            it.value.not()
        }.map {
            it.key
        }

        var state = when (deniedList.isEmpty()) {
            true -> PermissionState.Granted
            false -> PermissionState.Denied
        }

        if (state == PermissionState.Denied) {
            val permanentlyMappedList = deniedList.map {
                activity?.let { activity ->
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
                }
            }

            if (permanentlyMappedList.contains(false)) {
                state = PermissionState.PermanentlyDenied
            }
        }
        return state
    }

    fun Permission.launchMultiplePermissionForLocation() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        launchMultiplePermission(permissions)
    }

    private fun Permission.launchSinglePermission(permission: String) {
        this.result.launch(arrayOf(permission))
    }

    private fun Permission.launchMultiplePermission(permissionList: Array<String>) {
        this.result.launch(permissionList)
    }

    fun Fragment.registerPermission(onPermissionResult: (PermissionState) -> Unit): Permission {
        return Permission(
            this.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                onPermissionResult(getPermissionState(activity, it.toMutableMap()))
            }
        )
    }

    fun AppCompatActivity.registerPermission(onPermissionResult: (PermissionState) -> Unit): Permission {
        return Permission(
            this.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                onPermissionResult(getPermissionState(this, it.toMutableMap()))
            }
        )
    }

    fun isLocationEnabled(context: Context) : Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            isLocationEnabledAfterP(context)
        } else {
            isLocationEnabledBeforeP(context)
        }
    }

    @TargetApi(Build.VERSION_CODES.P)
    private fun isLocationEnabledAfterP(context: Context) : Boolean {
        val locationManager : LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isLocationEnabled
    }

    @Suppress("DEPRECATION")
    private fun isLocationEnabledBeforeP(context: Context) : Boolean {
        val locationMode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE,
            Settings.Secure.LOCATION_MODE_OFF)
        return locationMode != Settings.Secure.LOCATION_MODE_OFF
    }

    fun getLocationPermissionSettingsIntent(): Intent {
        val applicationId: String = BuildConfigUtils.getApplicationId()
        return Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$applicationId")
        )
    }
}