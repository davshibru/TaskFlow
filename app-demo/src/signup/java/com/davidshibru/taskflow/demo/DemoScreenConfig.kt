package com.davidshibru.taskflow.demo

import androidx.compose.runtime.Composable
import com.davidshibru.taskflow.feature.signup.presentation.congratsScreen
import com.davidshibru.taskflow.feature.signup.presentation.signUpScreen

data object SignUpDemoRoute : DemoRoute
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
