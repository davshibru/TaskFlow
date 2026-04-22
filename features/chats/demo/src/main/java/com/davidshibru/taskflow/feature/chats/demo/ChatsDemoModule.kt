package com.davidshibru.taskflow.feature.chats.demo

import com.davidshibru.taskflow.feature.chats.domain.repositories.ChatsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ChatsDemoModule {

    @Binds
    fun bindChatsRepository(
        impl: DemoChatsRepository
    ): ChatsRepository
}