package com.davidshibru.taskflow.core.data.network.dto

import kotlin.reflect.KClass

class EventType<T : Any>(
    val serializedTypeName: String,
    val kClass: KClass<T>
)