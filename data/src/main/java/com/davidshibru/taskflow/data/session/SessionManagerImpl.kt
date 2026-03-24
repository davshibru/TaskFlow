package com.davidshibru.taskflow.data.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.davidshibru.taskflow.core.data.network.interceptor.AuthTokenProvider
import com.davidshibru.taskflow.data.SessionManager
import com.davidshibru.taskflow.data.SessionProvider
import com.davidshibru.taskflow.data.session.entities.AuthDataToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SessionManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SessionProvider, SessionManager, AuthTokenProvider {
    override fun getToken(): Flow<AuthDataToken> {
        return dataStore.data.map { preferences ->
            preferences[tokenKey]
                ?.let(AuthDataToken::Default)
                ?: AuthDataToken.Empty
        }
    }

    override suspend fun saveToken(token: AuthDataToken) {
        dataStore.edit { preferences ->
            when (token) {
                is AuthDataToken.Default -> preferences[tokenKey] = token.accessToken
                AuthDataToken.Empty -> preferences.remove(tokenKey)
            }
        }
    }

    override fun provideToken(): String? {
        return runBlocking {
            (getToken().first() as? AuthDataToken.Default)?.accessToken
        }
    }

    private companion object {
        val tokenKey = stringPreferencesKey("token")
    }
}