package com.davidshibru.taskflow.features.signin.demo

import com.davidshibru.taskflow.features.signin.domain.repositories.LocalTokenRepository
import com.davidshibru.taskflow.features.signin.domain.repositories.SignInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DemoSignInModule {

    @Binds
    fun bindLocalTokenRepository(impl: DemoLocalTokenRepository): LocalTokenRepository

    @Binds
    fun bindSignInRepository(impl: DemoSignInRepository): SignInRepository
}