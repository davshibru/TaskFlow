package com.davidshibru.taskflow.core.navigation.di

import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.impl.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppNavigatorModule {

    @Binds
    fun bindsAppNavigator(
        impl: AppNavigatorImpl,
    ): AppNavigator

}

