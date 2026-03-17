package com.davidshibru.taskflow.glue.init.di

import com.davidshibru.taskflow.feature.init.domain.repositories.AuthRepository
import com.davidshibru.taskflow.feature.init.domain.repositories.DateTimeRepository
import com.davidshibru.taskflow.feature.init.domain.repositories.KeyFeatureRepository
import com.davidshibru.taskflow.glue.init.InitAuthRepository
import com.davidshibru.taskflow.glue.init.InitDateTimeRepository
import com.davidshibru.taskflow.glue.init.InitKeyFeatureRepository
import com.davidshibru.taskflow.glue.init.mappers.KeyFeatureMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface InitModule {

    @Binds
    fun bindAuthRepository(impl: InitAuthRepository) : AuthRepository

    @Binds
    fun bindDateTimeRepository(impl: InitDateTimeRepository) : DateTimeRepository

    @Binds
    fun bindKeyFeatureRepository(impl: InitKeyFeatureRepository) : KeyFeatureRepository

    @Binds
    fun bindKeyFeatureMapper(impl: KeyFeatureMapper.Default) : KeyFeatureMapper
}