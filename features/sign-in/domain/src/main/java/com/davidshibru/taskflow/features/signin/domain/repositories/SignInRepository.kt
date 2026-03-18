package com.davidshibru.taskflow.features.signin.domain.repositories

import com.davidshibru.taskflow.features.signin.domain.entities.Credentials
import com.davidshibru.taskflow.features.signin.domain.entities.Token

interface SignInRepository {
    suspend fun signIn(credentials: Credentials): Token
}
