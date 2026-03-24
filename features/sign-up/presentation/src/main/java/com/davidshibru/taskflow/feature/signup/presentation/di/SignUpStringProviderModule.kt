package com.davidshibru.taskflow.feature.signup.presentation.di

import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider
import com.davidshibru.taskflow.feature.signup.presentation.resources.SignUpStringProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SignUpStringProviderModule {

    @Binds
    fun bindSignUpStringProvider(impl: SignUpStringProviderImpl): SignUpStringProvider


}