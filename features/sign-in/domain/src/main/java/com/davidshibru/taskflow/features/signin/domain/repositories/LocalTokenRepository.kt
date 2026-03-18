package com.davidshibru.taskflow.features.signin.domain.repositories

import com.davidshibru.taskflow.features.signin.domain.entities.Token

interface LocalTokenRepository {
    suspend fun saveToken(token: Token)
}
