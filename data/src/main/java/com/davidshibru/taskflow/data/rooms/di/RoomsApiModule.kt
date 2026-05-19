package com.davidshibru.taskflow.data.rooms.di

import com.davidshibru.taskflow.data.rooms.remote.RoomsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object RoomsApiModule {

    @Provides
    fun provideRoomsApi(retrofit: Retrofit): RoomsApi {
        return retrofit.create(RoomsApi::class.java)
    }
}