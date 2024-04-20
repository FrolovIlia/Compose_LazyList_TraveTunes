package io.travel_tunes.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.database.database
import io.travel_tunes.data.UserFirebase
import io.travel_tunes.databinding.ActivitySplashBinding
import io.travel_tunes.utils.prefs.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        synchronizeFirebase()
    }

    private fun startMain() {
        Handler(Looper.getMainLooper()).postDelayed(
            { startActivity(MainActivity.getInstance(this)) },
            3_000
        )
        Handler(Looper.getMainLooper()).postDelayed({ this.finish() }, 3_000)
    }

    private fun synchronizeFirebase() {
        val usersDao =
            Firebase.database("https://traveltunes-36617-default-rtdb.europe-west1.firebasedatabase.app").reference
        val preferenceManager = PreferenceManager(this@SplashActivity)
        lifecycleScope.launch(Dispatchers.Main) {
            preferenceManager.uniqueIdFlow.collect { uniqueIdFromPrefs ->
                Timber.e("uniqueIdFromPrefs = $uniqueIdFromPrefs")
                val uniqueId = uniqueIdFromPrefs.orEmpty().ifBlank { UUID.randomUUID().toString() }
                if (uniqueId != uniqueIdFromPrefs) {
                    preferenceManager.setUniqueId(uniqueId)
                    return@collect
                }
                usersDao.child("users").child(uniqueId)
                    .setValue(UserFirebase(uniqueId, listOf("route_1"))).addOnSuccessListener {
                    // Write was successful!
                    // ...
                }
                    .addOnFailureListener {
                        // Write failed
                        // ...
                    }
                usersDao.child("users").child(uniqueId).get().addOnSuccessListener { result ->
                    val resultHashMap = result.value as? HashMap<*, *>?
                    resultHashMap?.let { hashMap ->
                        @Suppress("UNCHECKED_CAST") val userFirebase = UserFirebase(
                            userId = resultHashMap["userId"].toString(),
                            userRoutes = resultHashMap["userRoutes"] as? List<String> ?: emptyList()
                        )
                        Timber.i("firebase", "Got value $userFirebase")
                    }
                    startMain()
                }.addOnFailureListener {
                    Timber.e("firebase", "Error getting data", it)
                    startMain()
                }
            }
        }
    }
}