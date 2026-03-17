package com.davidshibru.taskflow.data.datetime.di

import com.davidshibru.taskflow.data.DateTimeDataRepository
import com.davidshibru.taskflow.data.datetime.DateTimeDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DateTimeModule {

    @Binds
    fun bindDateTimeDataRepository(impl: DateTimeDataRepositoryImpl): DateTimeDataRepository
}