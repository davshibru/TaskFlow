package com.davidshibru.taskflow.feature.signup.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.toolbar
import com.davidshibru.taskflow.core.theme.components.ContainerView


fun ScreenScope.signUpScreen() {
    content {
        val viewModel: SignUpViewModel = hiltViewModel()

        toolbar {
            title = "Sign Un"
            action(Icons.Default.Info, "first") {
                Logger.d("ℹ️ Нажата кнопка Info в Тулбаре")
            }
        }

        val container: Container<SignUpViewModel.State> by viewModel.stateFlow.collectAsState()

        ContainerView(
            container = container,
        ) { state ->
            SignUpContent(
                state = state,
            )
        }
    }
}

@Composable
private fun SignUpContent(state: SignUpViewModel.State) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = state.title)
    }
}