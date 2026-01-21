package com.davidshibru.taskflow.core.common.android.di

import com.davidshibru.taskflow.core.common.android.AndroidLogger
import com.davidshibru.taskflow.core.common.android.CoreStringProviderImpl
import com.davidshibru.taskflow.core.essentials.exceptions.mapper.DefaultExceptionToMessageMapper
import com.davidshibru.taskflow.core.essentials.exceptions.mapper.ExceptionToMessageMapper
import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider
import com.davidshibru.taskflow.core.essentials.resources.StringProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
interface CommonAndroidModule {

    @Binds
    fun bindLogger(
        logger: AndroidLogger
    ): Logger

    @Binds
    @IntoMap
    @ClassKey(CoreStringProvider::class)
    fun bindCoreStringProvider(
        impl: CoreStringProviderImpl
    ) : StringProvider

    @Binds
    fun bindExceptionToMessageMapper(
        impl: DefaultExceptionToMessageMapper
    ): ExceptionToMessageMapper
}