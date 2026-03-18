@file:OptIn(ExperimentalSerializationApi::class)

package com.davidshibru.taskflow.core.data.network.conventer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

internal fun createDefaultJson(
    isDebug: Boolean,
): Json {
    return Json {
        if (isDebug) {
            prettyPrint = true
        }
        explicitNulls = false
        encodeDefaults = true
        ignoreUnknownKeys = true
        namingStrategy = JsonNamingStrategy.SnakeCase
    }
}