package com.davidshibru.taskflow.glue.signin.mapper

import com.davidshibru.taskflow.data.accounts.entities.AuthDataCredentials
import com.davidshibru.taskflow.features.signin.domain.entities.Credentials
import javax.inject.Inject

interface MapperCredentials {
    suspend fun toAuthDataCredentials(credentials: Credentials) : AuthDataCredentials

    class Default @Inject constructor() : MapperCredentials {
        override suspend fun toAuthDataCredentials(credentials: Credentials): AuthDataCredentials {
            return with(credentials) {
                AuthDataCredentials(
                    login = login,
                    password = password,
                )
            }
        }

    }
}