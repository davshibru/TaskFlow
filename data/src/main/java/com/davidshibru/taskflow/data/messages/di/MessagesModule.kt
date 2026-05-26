package com.davidshibru.taskflow.data.messages.di

import com.davidshibru.taskflow.data.MessagesDataRepository
import com.davidshibru.taskflow.data.messages.MessagesDataRepositoryImpl
import com.davidshibru.taskflow.data.rooms.fetchers.RoomBasicMessagesInfoFetcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MessagesModule {

    @Binds
    fun bindMessagesDataRepository(impl: MessagesDataRepositoryImpl): MessagesDataRepository

    @Binds
    fun bindRoomBasicMessagesInfoFetcher(impl: MessagesDataRepositoryImpl): RoomBasicMessagesInfoFetcher
}