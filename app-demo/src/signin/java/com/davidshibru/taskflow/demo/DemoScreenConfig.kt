package com.davidshibru.taskflow.demo

import androidx.compose.runtime.Composable

import com.davidshibru.taskflow.features.signin.presentation.signInScreen

@Composable
fun DemoScreen() {
    ProvideDemoScreen {
        signInScreen()
    }
}