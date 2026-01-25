package com.davidshibru.taskflow.feature.init.presentation.di

import com.davidshibru.taskflow.core.essentials.resources.StringProvider
import com.davidshibru.taskflow.feature.init.domain.resources.InitStringProvider
import com.davidshibru.taskflow.feature.init.presentation.resources.InitStringProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
interface InitStringResourceModule {

    @Binds
    @IntoMap
    @ClassKey(InitStringProvider::class)
    fun bindInitStringProvider(
        impl: InitStringProviderImpl
    ) : StringProvider

}