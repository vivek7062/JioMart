package com.example.jiomartclone.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(name = "user_pref")
@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context){
    companion object {
        val TOKEN_KEY = stringPreferencesKey("token")
        val IS_LOGIN_KEY = booleanPreferencesKey("is_login")
    }

    suspend fun saveToken(token : String){
        context.dataStore.edit { pref->
            pref[TOKEN_KEY] = token
            pref[IS_LOGIN_KEY] = true
        }
    }

    suspend fun getToken(): String?{
        return context.dataStore.data.first()[TOKEN_KEY]
    }

    suspend fun clear(){
        context.dataStore.edit {
            it.clear()
        }
    }
}