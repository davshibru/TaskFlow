package com.davidshibru.taskflow.demo

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.davidshibru.taskflow.feature.chats.presentation.chatsScreen
import com.davidshibru.taskflow.features.main.presentation.Tab
import com.davidshibru.taskflow.features.main.presentation.mainScreen
import com.davidshibru.taskflow.features.profile.presentation.profileScreen
import kotlinx.serialization.Serializable

import com.davidshibru.taskflow.feature.chats.presentation.R as ChatsR
import com.davidshibru.taskflow.features.profile.presentation.R as ProfileR

@Serializable
private data object MainDemoRoute : DemoRoute

@Serializable
private data object ChatsDemoRoute : DemoRoute, Tab {
    override val icon = Icons.AutoMirrored.Default.Message
    override val label: Context.() -> String = { getString(ChatsR.string.chats_title) }
}

@Serializable
private data object ProfileDemoRoute : DemoRoute, Tab {
    override val icon: ImageVector = Icons.Default.AccountBox
    override val label: Context.() -> String = { getString(ProfileR.string.profile_title) }
}

@Composable
fun DemoScreen(demoNavigator: DemoNavigator) {
    ProvideDemoNavigation(
        navigator = demoNavigator,
        startDestination = MainDemoRoute,
    ) {
        composable<MainDemoRoute> { mainScreen(ChatsDemoRoute, ProfileDemoRoute) }
        composable<ChatsDemoRoute> { chatsScreen() }
        composable<ProfileDemoRoute> { profileScreen() }
    }
}
