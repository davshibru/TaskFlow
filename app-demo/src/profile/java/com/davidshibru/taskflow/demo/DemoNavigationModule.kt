package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.features.profile.presentation.ProfileRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DemoNavigationModule {

    @Binds
    fun bindProfileRouter(impl: DemoProfileRouter): ProfileRouter
}