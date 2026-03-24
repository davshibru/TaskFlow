package com.davidshibru.taskflow.glue.signin.di

import com.davidshibru.taskflow.features.signin.domain.repositories.LocalTokenRepository
import com.davidshibru.taskflow.features.signin.domain.repositories.SignInRepository
import com.davidshibru.taskflow.glue.signin.SignInFeatureRepository
import com.davidshibru.taskflow.glue.signin.SignInLocalTokenRepository
import com.davidshibru.taskflow.glue.signin.mapper.MapperCredentials
import com.davidshibru.taskflow.glue.signin.mapper.MapperToken
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface SignInModule {

    @Binds
    fun bindMapperCredentials(impl: MapperCredentials.Default) : MapperCredentials

    @Binds
    fun bindMapperToken(impl: MapperToken.Default) : MapperToken

    @Binds
    fun bindSignInRepository(impl: SignInFeatureRepository) : SignInRepository

    @Binds
    fun bindLocalTokenRepository(impl: SignInLocalTokenRepository) : LocalTokenRepository


}