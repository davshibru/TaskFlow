package com.davidshibru.taskflow.core.common.android.di

import com.davidshibru.taskflow.core.common.android.AndroidExceptionHandler
import com.davidshibru.taskflow.core.essentials.exceptions.handler.ExceptionHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@Module
@InstallIn(ActivityRetainedComponent::class)
interface ExceptionHandlerModule {

    @Binds
    fun bindExceptionHandler(
        impl: AndroidExceptionHandler
    ): ExceptionHandler
}