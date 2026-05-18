package com.davidshibru.taskflow.demo

import androidx.compose.runtime.Composable
import com.davidshibru.taskflow.features.profile.presentation.profileScreen
import kotlinx.serialization.Serializable

@Serializable
private data object ProfileDemoRoute : DemoRoute

@Composable
fun DemoScreen(demoNavigator: DemoNavigator) {
    ProvideDemoNavigation(
        navigator = demoNavigator,
        startDestination = ProfileDemoRoute,
    ) {
        composable<ProfileDemoRoute> { profileScreen() }
    }
}