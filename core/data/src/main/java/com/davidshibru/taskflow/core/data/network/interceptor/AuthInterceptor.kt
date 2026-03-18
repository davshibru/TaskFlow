package com.davidshibru.taskflow.core.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AuthInterceptor @Inject constructor(
    private val tokenProvider: AuthTokenProvider,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.provideToken()
        return if (token != null) {
            val updatedRequest = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(updatedRequest)
        } else {
            chain.proceed(chain.request())
        }
    }
}

interface AuthTokenProvider {
    fun provideToken(): String?
}