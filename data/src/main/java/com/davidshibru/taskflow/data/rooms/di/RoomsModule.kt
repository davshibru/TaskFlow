package com.davidshibru.taskflow.data.rooms.di

import com.davidshibru.taskflow.data.RoomsDataRepository
import com.davidshibru.taskflow.data.rooms.RoomFetcher
import com.davidshibru.taskflow.data.rooms.RoomFetcherImpl
import com.davidshibru.taskflow.data.rooms.RoomsDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RoomsModule {

    @Binds
    fun bindRoomsDataRepository(impl: RoomsDataRepositoryImpl): RoomsDataRepository

    @Binds
    fun bindRoomFetcherFactory(impl: RoomFetcherImpl.FactoryImpl): RoomFetcher.Factory
}