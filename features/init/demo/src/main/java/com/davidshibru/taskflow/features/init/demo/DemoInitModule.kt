package com.davidshibru.taskflow.features.init.demo

import com.davidshibru.taskflow.feature.init.domain.repositories.AuthRepository
import com.davidshibru.taskflow.feature.init.domain.repositories.KeyFeatureRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DemoInitModule {

    @Binds
    fun bindAuthRepository(impl: DemoAuthRepository) : AuthRepository

    @Binds
    fun bindKeyFeatureRepository(impl: DemoKeyFeatureRepository) : KeyFeatureRepository
}