package com.davidshibru.taskflow.feature.signup.presentation.di

import com.davidshibru.taskflow.core.essentials.resources.StringProvider
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider
import com.davidshibru.taskflow.feature.signup.presentation.resources.SignUpStringProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
interface SignUpStringProviderModule {


    @Binds
    @IntoMap
    @ClassKey(SignUpStringProvider::class)
    fun bindSignUpStringProviderIntoMap(
        impl: SignUpStringProviderImpl
    ): StringProvider

    @Binds
    fun bindSignUpStringProvider(impl: SignUpStringProviderImpl): SignUpStringProvider


}