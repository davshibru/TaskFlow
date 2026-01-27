package com.davidshibru.taskflow.feature.init.domain

import kotlinx.coroutines.delay
import javax.inject.Inject

// todo
class IsAuthorizedUseCase @Inject constructor() {
    suspend fun invoke(): Boolean {
        delay(2000)
        return false
    }
}