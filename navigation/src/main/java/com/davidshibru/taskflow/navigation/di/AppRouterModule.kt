package com.davidshibru.taskflow.navigation.di

import com.davidshibru.taskflow.navigation.base.AppRouter
import com.davidshibru.taskflow.navigation.base.NavComponentAppRouter
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