package com.davidshibru.taskflow.glue.signup.di

import com.davidshibru.taskflow.feature.signup.domain.repositories.CreateAccountRepository
import com.davidshibru.taskflow.feature.signup.domain.repositories.LoginAvailabilityRepository
import com.davidshibru.taskflow.glue.signup.SignUpCreateAccountRepository
import com.davidshibru.taskflow.glue.signup.SignUpLoginAvailabilityRepository
import com.davidshibru.taskflow.glue.signup.mapper.MapperNewAccount
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface SignUpModule {

    @Binds
    fun bindMapperNewAccount(
        impl: MapperNewAccount.Default
    ): MapperNewAccount

    @Binds
    fun bindLoginAvailabilityRepository(
        impl: SignUpLoginAvailabilityRepository
    ): LoginAvailabilityRepository

    @Binds
    fun bindCreateAccountRepository(
        impl: SignUpCreateAccountRepository
    ): CreateAccountRepository

}