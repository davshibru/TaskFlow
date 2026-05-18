package com.davidshibru.taskflow.features.profile.presentation.di

import com.davidshibru.taskflow.core.essentials.resources.StringProvider
import com.davidshibru.taskflow.features.profile.domain.resources.ProfileStringProvider
import com.davidshibru.taskflow.features.profile.presentation.resources.ProfileStringProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
interface ProfileStringProviderModule {

    @Binds
    @IntoMap
    @ClassKey(ProfileStringProvider::class)
    fun bindProfileStringProviderIntoMap(
        impl: ProfileStringProviderImpl,
    ): StringProvider

    @Binds
    fun bindProfileStringProvider(
        impl: ProfileStringProviderImpl,
    ): ProfileStringProvider
}