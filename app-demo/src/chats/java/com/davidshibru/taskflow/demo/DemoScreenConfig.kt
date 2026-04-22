package com.davidshibru.taskflow.demo

import androidx.compose.runtime.Composable
import com.davidshibru.taskflow.feature.chats.presentation.chatsScreen
import kotlinx.serialization.Serializable

@Serializable
private data object ChatsDemoRoute : DemoRoute

@Composable
fun DemoScreen(demoNavigator: DemoNavigator) {
    ProvideDemoNavigation(
        navigator = demoNavigator,
        startDestination = ChatsDemoRoute,
    ) {
        composable<ChatsDemoRoute> { chatsScreen() }
    }
}
