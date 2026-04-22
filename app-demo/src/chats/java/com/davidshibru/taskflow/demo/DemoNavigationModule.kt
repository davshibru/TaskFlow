package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.feature.chats.presentation.ChatsRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DemoNavigationModule {

    @Binds
    fun bindChatsRouter(impl: DemoChatsRouter): ChatsRouter
}
