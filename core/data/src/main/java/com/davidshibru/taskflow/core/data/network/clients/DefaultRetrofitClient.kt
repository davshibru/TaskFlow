package com.davidshibru.taskflow.core.data.network.clients

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

internal fun createDefaultRetrofit(
    baseUrl: String,
    client: OkHttpClient,
    callAdapterFactory:  CallAdapter.Factory,
    json: Json,
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .addCallAdapterFactory(callAdapterFactory)
        .build()
}