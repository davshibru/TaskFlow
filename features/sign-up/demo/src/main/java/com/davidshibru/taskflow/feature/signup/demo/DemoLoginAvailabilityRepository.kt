package com.davidshibru.taskflow.feature.signup.demo

import com.davidshibru.taskflow.feature.signup.domain.repositories.LoginAvailabilityRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

internal class DemoLoginAvailabilityRepository @Inject constructor(): LoginAvailabilityRepository {

    override suspend fun isLoginAvailable(login: String): Boolean {
        delay(1000)
        return login != "admin"
    }

}