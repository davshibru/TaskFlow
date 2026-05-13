package com.davidshibru.taskflow.core.navigation.di

import com.davidshibru.taskflow.core.navigation.routers.ChatsRouterImpl
import com.davidshibru.taskflow.core.navigation.routers.InitRouterImpl
import com.davidshibru.taskflow.core.navigation.routers.MainRouterImpl
import com.davidshibru.taskflow.core.navigation.routers.SignInRouterImpl
import com.davidshibru.taskflow.core.navigation.routers.SignUpRouterImpl
import com.davidshibru.taskflow.feature.chats.presentation.ChatsRouter
import com.davidshibru.taskflow.feature.init.presentation.InitRouter
import com.davidshibru.taskflow.feature.signup.presentation.SignUpRouter
import com.davidshibru.taskflow.features.main.presentation.MainRouter
import com.davidshibru.taskflow.features.signin.presentation.SignInRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RoutersModule {

    @Binds
    fun bindChatsRouter(
        chatsRouterImpl: ChatsRouterImpl,
    ): ChatsRouter

    @Binds
    fun bindInitRouter(
        initRouterImpl: InitRouterImpl
    ): InitRouter

    @Binds
    fun bindSignInRouter(
        signInRouterImpl: SignInRouterImpl,
    ): SignInRouter

    @Binds
    fun bindSignUpRouter(
        signUpRouterImpl: SignUpRouterImpl,
    ): SignUpRouter

    @Binds
    fun bindMainRouter(
        mainRouterImpl: MainRouterImpl,
    ): MainRouter
}
