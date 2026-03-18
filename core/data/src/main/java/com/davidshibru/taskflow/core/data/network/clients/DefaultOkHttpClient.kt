package com.davidshibru.taskflow.core.data.network.clients

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import kotlin.time.Duration

internal fun createOkHttpClient(
    timeout: Duration,
    interceptors: Iterable<Interceptor>,
): OkHttpClient {
    return OkHttpClient.Builder()
        .callTimeout(timeout)
        .apply {
            interceptors.forEach(::addInterceptor)
        }
        .build()
}