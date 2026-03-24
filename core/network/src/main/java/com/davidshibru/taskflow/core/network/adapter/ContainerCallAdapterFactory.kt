package com.davidshibru.taskflow.core.network.adapter

import com.davidshibru.taskflow.core.essentials.container.Container
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ContainerCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null

        check(returnType is ParameterizedType) { "Return type must be parameterized" }
        val callInnerType = getParameterUpperBound(0, returnType)

        val rawInnerType = getRawType(callInnerType)
        if (!Container::class.java.isAssignableFrom(rawInnerType)) {
            return null
        }

        check(callInnerType is ParameterizedType) { "Container must be parameterized" }
        val successBodyType = getParameterUpperBound(0, callInnerType)

        val mapErrorAnnotation = annotations
            .filterIsInstance<MapHttpCodeToException>()

        return object : CallAdapter<Any, Call<Container<Any>>> {
            override fun responseType(): Type = successBodyType

            override fun adapt(call: Call<Any>): Call<Container<Any>> {
                return ContainerCall(call, mapErrorAnnotation)
            }
        }
    }
}