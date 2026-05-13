package com.davidshibru.taskflow.features.signin.demo

import com.davidshibru.taskflow.features.signin.domain.entities.Credentials
import com.davidshibru.taskflow.features.signin.domain.entities.Token
import com.davidshibru.taskflow.features.signin.domain.repositories.SignInRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DemoSignInRepository @Inject constructor() : SignInRepository {
    override suspend fun signIn(credentials: Credentials): Token {
        delay(2000L)
        if (credentials.login == "admin" && credentials.password == "123") {
            return Token("demo-token")
        } else {
            throw Exception("Invalid credentials")
        }
    }
}
