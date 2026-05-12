package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.features.main.presentation.MainRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DemoNavigationModule {

    @Binds
    fun bindMainRouter(impl: DemoMainRouter): MainRouter
}