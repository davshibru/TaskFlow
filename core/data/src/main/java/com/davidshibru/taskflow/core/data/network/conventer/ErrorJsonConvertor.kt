package com.davidshibru.taskflow.core.data.network.conventer

import kotlinx.serialization.json.Json

@PublishedApi
internal val errorJson = Json {
    explicitNulls = false
    ignoreUnknownKeys = true
}