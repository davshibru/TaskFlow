package com.davidshibru.taskflow.features.init.demo

import com.davidshibru.taskflow.feature.init.domain.repositories.AuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject


class DemoAuthRepository @Inject constructor() : AuthRepository {

    override suspend fun isAuthorized(): Boolean {
        delay(1000)
        return false
    }
}
