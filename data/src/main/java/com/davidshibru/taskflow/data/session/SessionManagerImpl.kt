package com.davidshibru.taskflow.data.session

import com.davidshibru.taskflow.data.SessionProvider
import com.davidshibru.taskflow.data.session.entities.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

internal class SessionManagerImpl @Inject constructor() : SessionProvider{
    override fun getToken(): Flow<Token> {
        return flowOf(Token.Empty)
    }
}