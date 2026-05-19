package com.davidshibru.taskflow.glue.chats.di

import com.davidshibru.taskflow.feature.chats.domain.repositories.ChatsRepository
import com.davidshibru.taskflow.glue.chats.ChatsRepositoryImpl
import com.davidshibru.taskflow.glue.chats.mapper.MapperRoom
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ChatsModule {

    @Binds
    fun bindChatsRepository(impl: ChatsRepositoryImpl): ChatsRepository

    @Binds
    fun bindMapperRoom(impl: MapperRoom.Default): MapperRoom

}