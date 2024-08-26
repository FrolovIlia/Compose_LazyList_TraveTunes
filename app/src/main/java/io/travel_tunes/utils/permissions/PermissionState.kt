package io.travel_tunes.utils.permissions

sealed class PermissionState {
    data object Granted : PermissionState()
    data object Denied : PermissionState()
    data object PermanentlyDenied : PermissionState()
}
