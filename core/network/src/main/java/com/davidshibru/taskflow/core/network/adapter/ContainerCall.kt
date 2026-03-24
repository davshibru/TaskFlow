package com.davidshibru.taskflow.core.network.adapter

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.exception.BackendException
import com.davidshibru.taskflow.core.essentials.exception.ConnectionException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ContainerCall<T>(
    private val delegate: Call<T>,
    private val mapErrorAnnotations: List<MapHttpCodeToException>,
) : Call<Container<T>> {
    override fun enqueue(callback: Callback<Container<T>?>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val containerResponse = if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null || response.code() == 204) {
                        Response.success(Container.Success(body as T) as Container<T>)
                    } else {
                        val exception = Exception("Response body is null")
                        Response.success(Container.Error(exception) as Container<T>)
                    }
                } else {
                    val matchingAnnotation = mapErrorAnnotations.find { it.httpCode == response.code() }
                    val exception = if (matchingAnnotation != null) {
                        try {
                            matchingAnnotation.kClass.java.getDeclaredConstructor().newInstance()
                        } catch (e: Exception) {
                            Exception(
                                "Failed to instantiate ${matchingAnnotation.kClass.simpleName}",
                                e
                            )
                        }
                    } else {
                        BackendException(
                            code = response.code(),
                            backendMessage = response.message()
                        )
                    }

                    Response.success(Container.Error(exception) as Container<T>)
                }

                @Suppress("UNCHECKED_CAST")
                callback.onResponse(this@ContainerCall as Call<Container<T>?>, containerResponse)
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                val exception = when (t) {
                    is IOException -> ConnectionException(t)
                    is Exception -> t
                    else -> Exception(t)
                }
                val containerResponse = Response.success(Container.Error(exception) as Container<T>)
                @Suppress("UNCHECKED_CAST")
                callback.onResponse(this@ContainerCall as Call<Container<T>?>, containerResponse)
            }

        })
    }

    override fun clone(): Call<Container<T>> = ContainerCall(delegate.clone(), mapErrorAnnotations)
    override fun execute(): Response<Container<T>> =
        throw UnsupportedOperationException("Not supported")

    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun cancel() = delegate.cancel()
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()
}
