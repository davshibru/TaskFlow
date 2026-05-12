package com.davidshibru.taskflow.features.main.demo

import com.davidshibru.taskflow.features.main.domain.repositories.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MainDemoModule {

    @Binds
    fun bindMainRepository(
        impl: DemoMainRepository
    ): MainRepository
}