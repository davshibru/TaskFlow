package com.davidshibru.taskflow.feature.signup.demo

import com.davidshibru.taskflow.feature.signup.domain.repositories.CreateAccountRepository
import com.davidshibru.taskflow.feature.signup.domain.repositories.LoginAvailabilityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DemoSignUpModule {

    @Binds
    fun bindLoginAvailabilityRepository(impl: DemoLoginAvailabilityRepository): LoginAvailabilityRepository

    @Binds
    fun bindCreateAccountRepository(impl: DemoCreateAccountRepository): CreateAccountRepository


}