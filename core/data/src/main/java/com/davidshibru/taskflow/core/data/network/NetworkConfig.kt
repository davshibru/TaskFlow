package com.davidshibru.taskflow.core.data.network

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class NetworkConfig(
    val baseUrl: String = "http://10.0.2.2:8008/_matrix/client/v3/",
    val timeout: Duration = 10.seconds,
    val isDebug: Boolean = true,
)

