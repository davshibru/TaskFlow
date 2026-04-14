package com.davidshibru.taskflow.demo

import androidx.compose.runtime.Composable

import com.davidshibru.taskflow.feature.init.presentation.initScreen

private data object InitDemoRoute : DemoRoute

@Composable
fun DemoScreen(demoNavigator: DemoNavigator) {
    ProvideDemoNavigation(
        navigator = demoNavigator,
        startDestination = InitDemoRoute,
    ) {
        composable<InitDemoRoute> { initScreen() }
    }
}
