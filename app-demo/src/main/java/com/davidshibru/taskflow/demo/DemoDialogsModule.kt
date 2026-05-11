package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.core.essentials.dialogs.Dialogs
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DemoDialogsModule {

    @Binds
    fun bindDialogs(impl: DemoDialogs): Dialogs
}
