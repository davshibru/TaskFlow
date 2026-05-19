package com.davidshibru.taskflow.core.navigation.di

import com.davidshibru.taskflow.core.essentials.dialogs.Dialogs
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.impl.AppNavigatorImpl
import com.davidshibru.taskflow.core.navigation.base.impl.ComposeDialogs
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

    @Binds
    fun bindDialog(
        impl: ComposeDialogs,
    ): Dialogs

}