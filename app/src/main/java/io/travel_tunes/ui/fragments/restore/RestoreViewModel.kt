package io.travel_tunes.ui.fragments.restore

import com.google.firebase.Firebase
import com.google.firebase.database.database
import io.travel_tunes.R
import io.travel_tunes.utils.CrashlyticsUtils
import io.travel_tunes.utils.base.BaseViewModel
import io.travel_tunes.utils.base.BaseViewModelFactory
import io.travel_tunes.utils.prefs.PreferenceManager
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.util.UUID

class RestoreDataViewModel(
    private val preferenceManager: PreferenceManager,
    private val pathFirebaseDB: String
) : BaseViewModel() {

    private val usersDao by lazy { Firebase.database(pathFirebaseDB).reference }

    private var lastUniqueId: String? = null

    fun getUID(onUniqueIdReady: (uniqueId: String) -> Unit) {
        if (lastUniqueId.isNullOrBlank()) {
            launchAtViewModelScope {
                val deferred = async { preferenceManager.uniqueIdFlow.first() }
                val uniqueIdFromPrefs = deferred.await()
                val uniqueId = uniqueIdFromPrefs.orEmpty().ifBlank { UUID.randomUUID().toString() }
                if (uniqueId != uniqueIdFromPrefs) {
                    preferenceManager.setUniqueId(uniqueId)
                }
                lastUniqueId = uniqueId
                onUniqueIdReady(uniqueId)
            }
        } else {
            onUniqueIdReady(lastUniqueId!!)
        }
    }

    fun syncRemoteData(resultUpdate: (resultStringRes: Int) -> Unit) {
        getUID { uniqueId ->
            usersDao.child("users").child(uniqueId).get()
                .addOnSuccessListener { result ->
                    Timber.e("result = $result")
                    val resultHashMap =
                        result.value as? HashMap<*, *>? ?: return@addOnSuccessListener
                    Timber.e("resultHashMap = $resultHashMap")
                    resultHashMap.let {
                        @Suppress("UNCHECKED_CAST") val routes =
                            resultHashMap["userRoutes"] as? List<String> ?: emptyList()
                        if (routes.isNotEmpty()) {
                            Timber.e("routes = $routes")
                        } else {
                            Timber.e("routes are empty")
                        }
                    }
                    resultUpdate.invoke(R.string.restore_screen_restore_success)
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
}

class RestoreDataViewModelFactory(
    private val preferenceManager: PreferenceManager,
    private val pathFirebaseDB: String
) : BaseViewModelFactory<RestoreDataViewModel>() {
    override fun getViewModel(): RestoreDataViewModel {
        return RestoreDataViewModel(preferenceManager, pathFirebaseDB)
    }
}