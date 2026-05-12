package com.davidshibru.taskflow.features.main.presentation.di

import com.davidshibru.taskflow.core.essentials.resources.StringProvider
import com.davidshibru.taskflow.features.main.domain.resources.MainStringProvider
import com.davidshibru.taskflow.features.main.presentation.resources.MainStringProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
interface MainStringProviderModule {

    @Binds
    @IntoMap
    @ClassKey(MainStringProvider::class)
    fun bindMainStringProviderIntoMap(
        impl: MainStringProviderImpl,
    ): StringProvider

    @Binds
    fun bindMainStringProvider(
        impl: MainStringProviderImpl,
    ): MainStringProvider
}