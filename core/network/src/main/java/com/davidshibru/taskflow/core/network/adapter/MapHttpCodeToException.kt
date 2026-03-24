package com.davidshibru.taskflow.core.network.adapter

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Repeatable
@Retention(AnnotationRetention.RUNTIME)
annotation class MapHttpCodeToException(
    val httpCode: Int,
    val kClass: KClass<out Exception>
)