package org.d3if4003.jadwalbolaapp.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UIModeManager(val dataStore: DataStore<Preferences>) {
    companion object {
        val KEY_NIGHT_MODE = booleanPreferencesKey("NIGHT_MODE")
    }
    
    suspend fun saveData(isNightMode: Boolean){
        dataStore.edit {
            it[KEY_NIGHT_MODE] = isNightMode
        }
    }
    
    val isNightMode: Flow<Boolean?> = dataStore.data.map {
        it[KEY_NIGHT_MODE]
    }
}