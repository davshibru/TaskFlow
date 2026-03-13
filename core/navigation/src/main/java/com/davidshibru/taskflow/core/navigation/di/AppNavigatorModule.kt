package com.davidshibru.taskflow.core.navigation.di

import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AppNavigatorModule {

    @Binds
    fun bindsAppNavigator(
        impl: AppNavigatorImpl,
    ): AppNavigator

}

