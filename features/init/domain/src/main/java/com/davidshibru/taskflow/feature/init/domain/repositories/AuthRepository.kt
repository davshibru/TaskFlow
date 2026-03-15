package com.davidshibru.taskflow.feature.init.domain.repositories

import kotlinx.coroutines.delay
import javax.inject.Inject

// TODO use interface
class AuthRepository @Inject constructor() {

    suspend fun isAuthorized(): Boolean {
        delay(1000)
        return false
    }
}
