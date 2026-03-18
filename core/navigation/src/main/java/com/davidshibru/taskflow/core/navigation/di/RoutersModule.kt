package com.davidshibru.taskflow.core.navigation.di

import com.davidshibru.taskflow.core.navigation.routers.InitRouterImpl
import com.davidshibru.taskflow.core.navigation.routers.SignInRouterImpl
import com.davidshibru.taskflow.feature.init.presentation.InitRouter
import com.davidshibru.taskflow.features.signin.presentation.SignInRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RoutersModule {

    @Binds
    fun bindInitRouter(
        initRouterImpl: InitRouterImpl
    ): InitRouter

    @Binds
    fun bindSignInRouter(
        signInRouterImpl: SignInRouterImpl,
    ): SignInRouter
}