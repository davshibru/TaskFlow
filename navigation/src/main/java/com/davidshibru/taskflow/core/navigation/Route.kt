package com.davidshibru.taskflow.core.navigation

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.davidshibru.taskflow.core.navigation.dsl.BaseRoute
import com.davidshibru.taskflow.feature.chats.presentation.R
import com.davidshibru.taskflow.features.main.presentation.Tab
import kotlinx.serialization.Serializable

import com.davidshibru.taskflow.feature.chats.presentation.R as ChatsR

@Serializable
sealed interface Route : NavKey, BaseRoute

@Serializable
data object ChatsRoute : Route, Tab {
    override val icon = Icons.AutoMirrored.Default.Message
    override val label: Context.() -> String = { getString(ChatsR.string.chats_title) }
}

@Serializable
data object SignInRoute : Route, Tab {
    override val icon: ImageVector = Icons.Default.AccountBox
    override val label: Context.() -> String = { "Profile" }
}

@Serializable
data object InitRoute : Route

@Serializable
data object SignUpRoute : Route

@Serializable
data object CongratsRoute : Route

@Serializable
data object MainRoute : Route
