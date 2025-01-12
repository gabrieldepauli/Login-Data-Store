package br.edu.ifsp.dmo1.logindatastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import br.edu.ifsp.dmo1.logindatastore.util.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreRepository(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    object PreferencesFile {
        const val FILE_NAME = "user_preferences"
    }

    private object PreferencesKeys {
        val SAVE_LOGIN = booleanPreferencesKey("save_login")
        val STAY_LOGGED_IN = booleanPreferencesKey("stay_logged_in")
        val EMAIL = stringPreferencesKey("email")
        val PASSWORD = longPreferencesKey("password")
    }

    suspend fun savePreferences(email: String = "", password: Long = 0L, saveLogin: Boolean, stayLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EMAIL] = email
            preferences[PreferencesKeys.PASSWORD] = password
            preferences[PreferencesKeys.SAVE_LOGIN] = saveLogin
            preferences[PreferencesKeys.STAY_LOGGED_IN] = stayLoggedIn
        }
    }

    val loginPreferences: Flow<Pair<Boolean, Boolean>> = dataStore.data.map { preferences ->
        val saveLogin = preferences[PreferencesKeys.SAVE_LOGIN] ?: false
        val stayLoggedIn = preferences[PreferencesKeys.STAY_LOGGED_IN] ?: false
        Pair(saveLogin, stayLoggedIn)
    }

    val dataPreferences: Flow<Pair<String, Long>> = dataStore.data.map { preferences ->
        val email = preferences[PreferencesKeys.EMAIL] ?: ""
        val password = preferences[PreferencesKeys.PASSWORD] ?: 0L
        Pair(email, password)
    }

}