package com.davidshibru.taskflow.demo

import androidx.compose.runtime.Composable
import com.davidshibru.taskflow.feature.signup.presentation.congratsScreen
import com.davidshibru.taskflow.feature.signup.presentation.signUpScreen
import kotlinx.serialization.Serializable

@Serializable
data object SignUpDemoRoute : DemoRoute

@Serializable
data object CongratsDemoRoute : DemoRoute

@Composable
fun DemoScreen(demoNavigator: DemoNavigator) {
    ProvideDemoNavigation(
        navigator = demoNavigator,
        startDestination = SignUpDemoRoute,
    ) {
        composable<SignUpDemoRoute> { signUpScreen() }
        composable<CongratsDemoRoute> { congratsScreen() }
    }
}
