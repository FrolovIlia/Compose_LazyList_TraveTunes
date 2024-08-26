package io.travel_tunes.utils.permissions

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.travel_tunes.R
import io.travel_tunes.utils.ButtonDialogInfo
import io.travel_tunes.utils.DialogHelper
import io.travel_tunes.utils.OnSimpleClick
import io.travel_tunes.utils.extencions.isActiveState
import io.travel_tunes.utils.extencions.isLocationPermissionFineGranted
import io.travel_tunes.utils.permissions.PermissionUtils.getLocationPermissionSettingsIntent
import io.travel_tunes.utils.permissions.PermissionUtils.isLocationEnabled
import io.travel_tunes.utils.permissions.PermissionUtils.launchMultiplePermissionForLocation
import io.travel_tunes.utils.permissions.PermissionUtils.registerPermission
import timber.log.Timber

class PermissionLocationHelper(
    private val activity: AppCompatActivity,
    private val fragment: Fragment? = null,
    private val onPermissionGranted: (() -> Unit)? = null,
    private val onPermissionNonGranted: (() -> Unit)? = null
) {
    private var dialogLocation: AlertDialog? = null

    /**
     * isNeedRetryRequest
     * если true, значит надо повторно показать диалоговое окна
     * иначе - игнорим результат
     */
    private var isFirstRequest: Boolean = true

    /**
     * isSingleRequest
     * если true - игнорим негативный результат
     * иначе - значит надо повторно показать диалоговое окно (зациклено)
     */
    private var isSingleRequest: Boolean = false

    private var currentAction: OnSimpleClick? = null

    private val locationPermissionSettingsResult =
        fragment?.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            checkAndRequestPermission(isNeedRetry = false)
        } ?: activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            checkAndRequestPermission(isNeedRetry = false)
        }

    private val locationEnableResolutionForResult =
        fragment?.registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            handleLocationEnableResolutionResult(
                it
            )
        }
            ?: activity.registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
                handleLocationEnableResolutionResult(
                    it
                )
            }

    private val locationPermission =
        fragment?.registerPermission { handleLocationPermissionResult(it) }
            ?: activity.registerPermission { handleLocationPermissionResult(it) }

    private val onLocationEnableClickListener: OnSimpleClick = {
        GpsUtils(activity).turnGPSOn(
            onGpsListener = { isGPSEnable: Boolean ->
                Timber.e("isGPSEnable = $isGPSEnable")
                if (isGPSEnable) startAndClearAction()
            },
            onRaeExceptionListener = { raeResolution ->
                locationEnableResolutionForResult.launch(
                    IntentSenderRequest.Builder(raeResolution).build()
                )
            }
        )
    }

    /**
     * проверяет location Permission
     * - если выключен - то запрашивает включение
     * - если включен - то проверяет состояние gps
     *  - если выключено - запрашивает включение
     *  - если включено - то выполняем action
     *
     *  params:
     *  - action - for start after permissionGranted and locationEnabled
     *  - isSingleRequest - for manage - single or cycle requests
     */
    fun launchIfLocationPermissionGrantedAndLocationEnabled(
        action: OnSimpleClick, isSingleRequest: Boolean = true
    ) {
        this.isSingleRequest = isSingleRequest
        isFirstRequest = true
        currentAction = action

        if (activity.isLocationPermissionFineGranted()) {
            manageLocationPermissionDialogState(isPermissionGranted = true)
            onPermissionGranted?.invoke()
            checkAndRequestLocationEnable()
        } else {
            launchLocationPermission()
        }
    }

    private fun handleLocationPermissionResult(state: PermissionState) {
        when (state) {
            PermissionState.Granted -> {
                manageLocationPermissionDialogState(isPermissionGranted = true)
                onPermissionGranted?.invoke()
                checkAndRequestLocationEnable()
            }

            PermissionState.Denied -> {
                onPermissionNonGranted?.invoke()
                if (isSingleRequest && !isFirstRequest) {
                    return
                }
                isFirstRequest = false
                manageLocationPermissionDialogState(isPermissionGranted = false,
                    isPermanentlyDenied = false,
                    onClickListener = {
                        launchLocationPermission()
                    })
            }

            PermissionState.PermanentlyDenied -> {
                onPermissionNonGranted?.invoke()
                if (isSingleRequest && !isFirstRequest) {
                    return
                }
                isFirstRequest = false
                manageLocationPermissionDialogState(isPermissionGranted = false,
                    isPermanentlyDenied = true,
                    onClickListener = {
                        locationPermissionSettingsResult.launch(
                            getLocationPermissionSettingsIntent()
                        )
                    })

            }
        }
    }

    private fun checkAndRequestPermission(isNeedRetry: Boolean) {
        if (!activity.isLocationPermissionFineGranted()) {
            isFirstRequest = isNeedRetry
            launchLocationPermission()
        } else {
            checkAndRequestLocationEnable()
        }
    }

    private fun launchLocationPermission() {
        locationPermission.launchMultiplePermissionForLocation()
    }

    private fun checkAndRequestLocationEnable() {
        if (isLocationEnabled(activity)) {
            manageLocationEnableDialogState(
                isLocationEnable = true, onClickListener = onLocationEnableClickListener
            )
            // скрыть плашку с запросом
            // центрировать на местоположении юзера
        } else {
            // показать плашку типа включи gps
            manageLocationEnableDialogState(
                isLocationEnable = false, onClickListener = onLocationEnableClickListener
            )
        }
    }

    private fun manageLocationPermissionDialogState(
        isPermissionGranted: Boolean,
        onClickListener: OnSimpleClick? = null,
        isPermanentlyDenied: Boolean = false
    ) {
        when {
            isPermissionGranted -> {
                //hide dialog
                hideLocationPermissionDialog()
            }

            else -> {
                //показать диалог
                if (fragment?.lifecycle?.isActiveState() == true || activity.lifecycle.isActiveState()) {
                    showLocationPermissionDialog(
                        onClickListener = onClickListener!!,
                        isPermanentlyDenied = isPermanentlyDenied
                    )
                }
            }
        }
    }

    private fun manageLocationEnableDialogState(
        isLocationEnable: Boolean, onClickListener: OnSimpleClick
    ) {
        if (isLocationEnable) {
            //скрыть диалог
            hideLocationEnableDialog()
            startAndClearAction()
        } else {
            //показать диалог
            showLocationEnableDialog(
                onClickListener = onClickListener,
            )
        }
    }

    private fun showLocationPermissionDialog(
        onClickListener: OnSimpleClick,
        isPermanentlyDenied: Boolean = false,
    ) {
        if (dialogLocation == null) {
            val title = activity.resources.getString(R.string.dialog_location_permission_title)
            val content = activity.resources.getString(R.string.dialog_location_permission_content)
            val buttonPositive = if (isPermanentlyDenied)
                activity.resources.getString(R.string.dialog_location_permission_to_settings_btn)
            else
                activity.resources.getString(R.string.dialog_location_permission_positive_btn)
            val buttonNegative =
                activity.resources.getString(R.string.dialog_location_permission_negative_btn)
            val positiveButton = ButtonDialogInfo(text = buttonPositive, action = onClickListener)
            val negativeButton =
                ButtonDialogInfo(text = buttonNegative, action = { clearDialog() })
            dialogLocation = DialogHelper.createAlertDialog(
                context = activity,
                title = title,
                message = content,
                positiveButton = positiveButton,
                negativeButton = negativeButton,
                isDialogCancelable = true
            )
        }
    }

    private fun hideLocationPermissionDialog() {
        clearDialog()
    }

    private fun showLocationEnableDialog(
        onClickListener: OnSimpleClick
    ) {
        if (dialogLocation == null) {
            val title = activity.resources.getString(R.string.dialog_location_enable_title)
            val content = activity.resources.getString(R.string.dialog_location_enable_content)
            val buttonPositive =
                activity.resources.getString(R.string.dialog_location_enable_positive_btn)
            val buttonNegative =
                activity.resources.getString(R.string.dialog_location_permission_negative_btn)
            val positiveButton = ButtonDialogInfo(text = buttonPositive, action = onClickListener)
            val negativeButton =
                ButtonDialogInfo(text = buttonNegative, action = { clearDialog() })
            dialogLocation = DialogHelper.createAlertDialog(
                context = activity,
                title = title,
                message = content,
                positiveButton = positiveButton,
                negativeButton = negativeButton,
                isDialogCancelable = true
            )
        }
    }

    private fun hideLocationEnableDialog() {
        clearDialog()
    }

    private fun clearDialog() {
        dialogLocation?.dismiss()
        dialogLocation = null
    }

    private fun handleLocationEnableResolutionResult(activityResult: ActivityResult) {
        if (activityResult.resultCode == Activity.RESULT_OK) {
            // все успешно, пусть работает
            startAndClearAction()
        } else {
            // пусть все-таки включит
            if (isSingleRequest && !isFirstRequest) return
            isFirstRequest = false
            checkAndRequestLocationEnable()
        }
    }

    private fun startAndClearAction() {
        currentAction?.invoke()
    }
}