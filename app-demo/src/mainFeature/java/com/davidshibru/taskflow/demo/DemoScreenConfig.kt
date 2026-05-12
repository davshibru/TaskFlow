package com.davidshibru.taskflow.demo

import androidx.compose.runtime.Composable
import com.davidshibru.taskflow.features.main.presentation.mainScreen
import kotlinx.serialization.Serializable

@Serializable
private data object MainDemoRoute : DemoRoute

@Composable
fun DemoScreen(demoNavigator: DemoNavigator) {
    ProvideDemoNavigation(
        navigator = demoNavigator,
        startDestination = MainDemoRoute,
    ) {
        composable<MainDemoRoute> { mainScreen() }
    }
}