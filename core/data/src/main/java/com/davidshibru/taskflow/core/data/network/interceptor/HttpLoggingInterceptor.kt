package com.davidshibru.taskflow.core.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

internal fun createHttpLoggingInterceptor(
    isDebug: Boolean,
): Interceptor {
    val level = if (isDebug) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.BASIC
    }
    return HttpLoggingInterceptor().setLevel(level)
}