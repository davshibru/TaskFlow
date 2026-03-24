package com.davidshibru.taskflow.feature.signup.domain.repositories

interface LoginAvailabilityRepository {

    suspend fun isLoginAvailable(login: String): Boolean

}