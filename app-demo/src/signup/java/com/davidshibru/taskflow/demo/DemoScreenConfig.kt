package com.davidshibru.taskflow.demo

import androidx.compose.runtime.Composable
import com.davidshibru.taskflow.feature.signup.presentation.signUpScreen


@Composable
fun DemoScreen() {
    ProvideDemoScreen {
        signUpScreen()
    }
}