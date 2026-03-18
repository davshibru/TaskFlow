package com.davidshibru.taskflow.data

import com.davidshibru.taskflow.data.session.entities.AuthDataToken
import kotlinx.coroutines.flow.Flow

interface SessionProvider {

    fun getToken(): Flow<AuthDataToken>
}