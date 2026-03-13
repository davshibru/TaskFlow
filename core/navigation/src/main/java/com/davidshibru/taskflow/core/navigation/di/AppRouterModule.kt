package com.davidshibru.taskflow.core.navigation.di

import com.davidshibru.taskflow.core.navigation.base.AppRouter
import com.davidshibru.taskflow.core.navigation.base.NavComponentAppRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AppRouterModule {

    @Binds
    fun bindAppRouter(
        impl: NavComponentAppRouter
    ): AppRouter

}

