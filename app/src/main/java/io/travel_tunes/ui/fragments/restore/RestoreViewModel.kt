package io.travel_tunes.ui.fragments.restore

import com.google.firebase.Firebase
import com.google.firebase.database.database
import io.travel_tunes.R
import io.travel_tunes.data.repository.DefaultRepository
import io.travel_tunes.di.FirebaseDBInfo
import io.travel_tunes.model.UserDeviceData
import io.travel_tunes.utils.CrashlyticsUtils
import io.travel_tunes.utils.base.BaseViewModel
import io.travel_tunes.utils.base.BaseViewModelFactory
import io.travel_tunes.utils.content.ProjectSetup
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

class RestoreDataViewModel(
    private val defaultRepository: DefaultRepository,
    private val pathFirebaseDB: String
) : BaseViewModel() {

    private val usersDao by lazy { Firebase.database(pathFirebaseDB).reference }

    private var lastUniqueId: String? = null
    private var userDeviceInfo: UserDeviceData? = null

    fun getUID(onUniqueIdReady: (uniqueId: String) -> Unit) {
        if (lastUniqueId.isNullOrBlank()) {
            launchAtViewModelScope {
                val uniqueId = defaultRepository.uniqueIdFlow.first()
                lastUniqueId = uniqueId
                sendToDBRequest(uniqueId)
                onUniqueIdReady(uniqueId)
            }
        } else {
            onUniqueIdReady(lastUniqueId!!)
            sendToDBRequest(lastUniqueId!!)
        }
    }

    fun syncRemoteData(resultUpdate: (resultStringRes: Int) -> Unit) {
        getUID { uniqueId ->
            usersDao.child(ProjectSetup.TABLE_NAME).child("users_payments").child(uniqueId).get()
                .addOnSuccessListener { result ->
                    Timber.e("result = $result")
                    val resultHashMap =
                        result.value as? HashMap<*, *>? ?: return@addOnSuccessListener
                    Timber.e("resultHashMap = $resultHashMap")
                    resultHashMap.let {
                        @Suppress("UNCHECKED_CAST") val payments =
                            resultHashMap["payments"] as? List<String> ?: emptyList()
                        if (payments.isNotEmpty()) {
                            Timber.e("payments = $payments")
                            addToPrefsInfo(payments.toSet())
                            resultUpdate.invoke(R.string.restore_screen_restore_success_with_changes)
                        } else {
                            Timber.e("payments are empty")
                            resultUpdate.invoke(R.string.restore_screen_restore_success)
                        }
                    }
                }
                .addOnCanceledListener {
                    CrashlyticsUtils.sendThrowableNonFatal("syncRemoteData cancelled")
                    resultUpdate.invoke(R.string.restore_screen_restore_error)
                }
                .addOnFailureListener {
                    CrashlyticsUtils.sendThrowableNonFatal(it)
                    resultUpdate.invoke(R.string.restore_screen_restore_error)
                }
        }
    }

    private fun sendToDBRequest(uniqueId: String) {
        userDeviceInfo = if (userDeviceInfo == null) {
            UserDeviceData(
                model = UserDeviceData.getDeviceInfoModel(),
                androidOS = UserDeviceData.getDeviceInfoOSVersion(),
                uniqueId = uniqueId,
                currentDateTime = UserDeviceData.getCurrentDateTime()
            )
        } else {
            userDeviceInfo?.copy(currentDateTime = UserDeviceData.getCurrentDateTime())
        }
        usersDao.child(ProjectSetup.TABLE_NAME).child("users_restore_requests").child(uniqueId)
            .setValue(userDeviceInfo)
            .addOnSuccessListener {
                Timber.e("add data success")
            }
            .addOnCanceledListener {
                CrashlyticsUtils.sendThrowableNonFatal("add data cancelable")
            }
            .addOnFailureListener {
                CrashlyticsUtils.sendThrowableNonFatal(it)
            }
            .addOnCompleteListener {
                Timber.e("add data complete")
            }
    }

    private fun addToPrefsInfo(newPaymentsInfo: Set<String>) {
        launchAtViewModelScope {
            val deferred = async { defaultRepository.updatePaymentsAndRoutesInfo(newPaymentsInfo) }
            deferred.await()
        }
    }
}

class RestoreDataViewModelFactory @Inject constructor(
    @FirebaseDBInfo private val pathFirebaseDB: String,
    private val defaultRepository: DefaultRepository,
) : BaseViewModelFactory<RestoreDataViewModel>() {

    companion object {
        private const val tag = "path_firebase_db"
    }

    override fun getViewModel(): RestoreDataViewModel {
        return RestoreDataViewModel(defaultRepository, pathFirebaseDB)
    }
}