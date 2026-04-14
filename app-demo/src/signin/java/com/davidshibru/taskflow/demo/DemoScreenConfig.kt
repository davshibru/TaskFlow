package com.davidshibru.taskflow.demo

import androidx.compose.runtime.Composable

import com.davidshibru.taskflow.features.signin.presentation.signInScreen

private data object SignInDemoRoute : DemoRoute

@Composable
fun DemoScreen(demoNavigator: DemoNavigator) {
    ProvideDemoNavigation(
        navigator = demoNavigator,
        startDestination = SignInDemoRoute,
    ) {
        composable<SignInDemoRoute> { signInScreen() }
    }
}
