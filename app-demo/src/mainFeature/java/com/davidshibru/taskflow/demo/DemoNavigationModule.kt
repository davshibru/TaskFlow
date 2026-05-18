package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.feature.chats.presentation.ChatsRouter
import com.davidshibru.taskflow.features.main.presentation.MainRouter
import com.davidshibru.taskflow.features.profile.presentation.ProfileRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DemoNavigationModule {

    @Binds
    fun bindMainRouter(impl: DemoMainRouter): MainRouter

    @Binds
    fun bindChatsRouter(impl: DemoChatsRouter): ChatsRouter

    @Binds
    fun bindProfileRouter(impl: DemoProfileRouter): ProfileRouter
}
