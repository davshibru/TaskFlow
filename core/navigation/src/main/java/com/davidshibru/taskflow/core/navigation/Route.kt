package com.davidshibru.taskflow.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route

@Serializable
data object InitRoute : Route

@Serializable
data object SignInRoute : Route

@Serializable
data object SignUpRoute : Route

@Serializable
data object CongratsRoute : Route