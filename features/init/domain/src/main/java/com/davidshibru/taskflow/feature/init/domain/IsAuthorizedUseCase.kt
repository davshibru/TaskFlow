package com.davidshibru.taskflow.feature.init.domain

import com.davidshibru.taskflow.core.essentials.exceptions.ConnectionException
import kotlinx.coroutines.delay
import javax.inject.Inject

// todo
class IsAuthorizedUseCase @Inject constructor() {
    suspend fun invoke(): Boolean {
        delay(2000)
        throw ConnectionException()
    }
}