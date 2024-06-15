package dev.mcarr.a2fauthcompose.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import dev.mcarr.a2fauthcompose.BuildConfig
import dev.mcarr.a2fauthcompose.util.Api
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection

class SetupScreenViewModel : ViewModel() {

    val keyEndpoint = stringPreferencesKey("KEY_ENDPOINT")
    val keyToken = stringPreferencesKey("KEY_TOKEN")
    val keyHttps = booleanPreferencesKey("KEY_HTTPS")
    val keyDebug = booleanPreferencesKey("KEY_DEBUG")
    val keyStoreSecrets = booleanPreferencesKey("KEY_STORE_SECRETS")

    var endpoint: String = ""
    var token: String = ""
    var httpsVerification: Boolean = true
    var debugMode: Boolean = false
    var storeSecrets: Boolean = false

    //If the endpoint or the token haven't been specified,
    //consider the viewmodel to be empty/not loaded
    fun isEmpty(): Boolean = endpoint == "" || token == ""

    //Sync read
    suspend fun readFromDataStore(dataStore: DataStore<Preferences>){
        dataStore.data.first().let {
            endpoint = it[keyEndpoint] ?: BuildConfig.API_DOMAIN
            token = it[keyToken] ?: BuildConfig.API_TOKEN
            httpsVerification = it[keyHttps] ?: BuildConfig.API_HTTPS_VERIFICATION
            debugMode = it[keyDebug] ?: BuildConfig.API_DEBUG_MODE
            storeSecrets = it[keyStoreSecrets] ?: BuildConfig.API_STORE_SECRETS
        }
    }

    //Async write
    suspend fun writeToDataStore(dataStore: DataStore<Preferences>) {
        dataStore.edit {
            it[keyEndpoint] = endpoint
            it[keyToken] = token
            it[keyHttps] = httpsVerification
            it[keyDebug] = debugMode
            it[keyStoreSecrets] = storeSecrets
        }
    }

}