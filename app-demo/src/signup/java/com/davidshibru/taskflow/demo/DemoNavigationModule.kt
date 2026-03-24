package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.feature.signup.presentation.SignUpRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DemoNavigationModule {
    @Binds
    fun bindSignUpRouter(impl: DemoSignUpRouter) : SignUpRouter
}