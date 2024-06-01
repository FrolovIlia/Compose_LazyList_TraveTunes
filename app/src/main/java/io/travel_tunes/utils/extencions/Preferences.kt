package io.travel_tunes.utils.extencions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
fun <T> DataStore<Preferences>.getValueFlow(
    key: Preferences.Key<T>, defaultValue: T
): Flow<T> {
    return this.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[key] ?: defaultValue
    }
}

suspend fun <T> DataStore<Preferences>.setValue(
    key: Preferences.Key<T>, value: T
): Boolean {
    var isChanges = false
    this.edit { preferences ->
        isChanges = preferences[key] != value
        if (isChanges) {
            preferences[key] = value
        }
    }.let {
        return isChanges
    }
}