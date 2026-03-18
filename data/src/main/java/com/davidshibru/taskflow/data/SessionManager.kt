package com.davidshibru.taskflow.data

import com.davidshibru.taskflow.data.session.entities.AuthDataToken

interface SessionManager : SessionProvider{

    suspend fun saveToken(token: AuthDataToken)
}