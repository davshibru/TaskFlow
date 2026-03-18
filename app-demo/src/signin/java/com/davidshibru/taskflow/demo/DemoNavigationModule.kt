package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.features.signin.presentation.SignInRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DemoNavigationModule {

    @Binds
    fun bindSignInRouter(impl: DemoSignInRoute) : SignInRouter
}