package com.davidshibru.taskflow.features.signin.demo

import com.davidshibru.taskflow.features.signin.domain.entities.Token
import com.davidshibru.taskflow.features.signin.domain.repositories.LocalTokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DemoLocalTokenRepository @Inject constructor() : LocalTokenRepository{
    override suspend fun saveToken(token: Token) {

    }
}