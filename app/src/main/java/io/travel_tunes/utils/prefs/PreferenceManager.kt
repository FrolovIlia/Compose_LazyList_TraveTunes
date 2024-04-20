package io.travel_tunes.utils.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferenceManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val UNIQUE_ID = stringPreferencesKey("unique_id")

    }

    val uniqueIdFlow = dataStore.data.map { preferences ->
        preferences[UNIQUE_ID]
    }

    suspend fun setUniqueId(uniqueId: String) {
        dataStore.edit { preferences ->
            preferences[UNIQUE_ID] = uniqueId
        }
    }
}

