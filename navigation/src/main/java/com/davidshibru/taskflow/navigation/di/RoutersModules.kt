package com.davidshibru.taskflow.navigation.di

import com.davidshibru.taskflow.feature.init.presentation.InitRouter
import com.davidshibru.taskflow.navigation.routers.InitRouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RoutersModules {

    @Binds
    fun bindInitRouter(impl: InitRouterImpl): InitRouter


}