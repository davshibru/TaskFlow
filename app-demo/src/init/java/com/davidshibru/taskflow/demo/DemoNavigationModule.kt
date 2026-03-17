package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.feature.init.presentation.InitRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DemoNavigationModule {
    @Binds
    fun bindInitRouter(impl: DemoInitRouter) : InitRouter
}