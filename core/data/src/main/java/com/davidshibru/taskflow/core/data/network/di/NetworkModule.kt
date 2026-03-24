package com.davidshibru.taskflow.core.data.network.di

import com.davidshibru.taskflow.core.data.network.NetworkConfig
import com.davidshibru.taskflow.core.data.network.clients.createDefaultRetrofit
import com.davidshibru.taskflow.core.data.network.clients.createOkHttpClient
import com.davidshibru.taskflow.core.data.network.conventer.createDefaultJson
import com.davidshibru.taskflow.core.data.network.interceptor.AuthInterceptor
import com.davidshibru.taskflow.core.data.network.interceptor.createHttpLoggingInterceptor
import com.davidshibru.taskflow.core.network.adapter.ContainerCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(networkConfig: NetworkConfig): Json {
        return createDefaultJson(networkConfig.isDebug)
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        networkConfig: NetworkConfig,
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        return createOkHttpClient(networkConfig.timeout,
            interceptors = listOf(
                createHttpLoggingInterceptor(networkConfig.isDebug),
                    authInterceptor
            )
        )
    }

    @Provides
    @Singleton
    fun provideCallAdapterFactory():  CallAdapter.Factory {
        return ContainerCallAdapterFactory()
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        networkConfig: NetworkConfig,
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory,
        json: Json,
    ): Retrofit {
        return createDefaultRetrofit(networkConfig.baseUrl, okHttpClient, callAdapterFactory, json)
    }
}