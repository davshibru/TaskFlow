package com.davidshibru.taskflow.features.signin.presentation.di

import com.davidshibru.taskflow.core.essentials.resources.StringProvider
import com.davidshibru.taskflow.features.signin.domain.resources.SignInStringProvider
import com.davidshibru.taskflow.features.signin.presentation.resources.SignInStringProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
interface SignInStringProviderModule {

    @Binds
    @IntoMap
    @ClassKey(SignInStringProvider::class)
    fun bindSignInStringProviderIntoMap(
        impl: SignInStringProviderImpl
    ): StringProvider

    @Binds
    fun bindSignInStringProvider(impl: SignInStringProviderImpl): SignInStringProvider
}