package com.davidshibru.taskflow.core.common.android.di

import com.davidshibru.taskflow.core.common.android.logger.AndroidLogger
import com.davidshibru.taskflow.core.common.android.resources.CommonAndroidStringProvider
import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.core.essentials.resources.StringProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CommonAndroidModule {

    @Binds
    fun bindLogger(
        logger: AndroidLogger
    ): Logger

    @Binds
    fun bindStringProvider(
        impl: CommonAndroidStringProvider
    ): StringProvider
}