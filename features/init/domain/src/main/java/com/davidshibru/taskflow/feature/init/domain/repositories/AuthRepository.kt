package com.davidshibru.taskflow.feature.init.domain.repositories


interface AuthRepository {

    suspend fun isAuthorized(): Boolean
}
