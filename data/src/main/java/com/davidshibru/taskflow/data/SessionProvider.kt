package com.davidshibru.taskflow.data

import com.davidshibru.taskflow.data.session.entities.Token
import kotlinx.coroutines.flow.Flow

interface SessionProvider {

    fun getToken(): Flow<Token>
}