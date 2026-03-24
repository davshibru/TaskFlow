package com.davidshibru.taskflow.di

import com.davidshibru.taskflow.core.data.network.NetworkConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppConfigModule {

    @Provides
    @Singleton
    fun provideNetworkConfig() = NetworkConfig(
//        isDebug = BuildConfig.IS_DEBUG
    )
}