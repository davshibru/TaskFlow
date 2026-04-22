package com.davidshibru.taskflow.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey

@Serializable
data object InitRoute : Route

@Serializable
data object SignInRoute : Route

@Serializable
data object SignUpRoute : Route

@Serializable
data object CongratsRoute : Route

@Serializable
data object ChatsRoute: Route