package com.davidshibru.taskflow.features.signin.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar
import com.davidshibru.taskflow.core.navigation.dsl.toolbar
import com.davidshibru.taskflow.core.theme.components.ContainerView

fun ScreenScope.signInScreen() {
    content {
        val viewModel: SignInViewModel = hiltViewModel()

        toolbar {
            title = "Sign in"
            action(Icons.Default.Info, "first") {
                Logger.d("ℹ️ Нажата кнопка Info в Тулбаре")
            }
            action(Icons.Default.Info, "second") {
                Logger.d("ℹ️ Нажата кнопка Info в Тулбаре")
            }
            action(Icons.Default.Info, "third") {
                Logger.d("ℹ️ Нажата кнопка Info в Тулбаре")
            }
            contextMenuAction {
                icon = Icons.Default.MoreVert
                item {
                    titleRes = R.string.terms_and_conditions
                    onClick = {
                        Logger.d("ℹ️ Нажата кнопка Terms and conditions в контекстном меню")
                    }
                }
                item {
                    titleRes = R.string.privacy_policy
                    onClick = {
                        Logger.d("ℹ️ Нажата кнопка Privacy policy в контекстном меню")
                    }
                }
            }
        }

        val container: Container<SignInViewModel.State> by viewModel.stateFlow.collectAsState()

        ContainerView(
            container = container,
        ) { state ->
            SignInContent(state)
        }
    }

}

@Composable
private fun BoxScope.SignInContent(state: SignInViewModel.State) {
    Text(
        text = state.title,
        modifier = Modifier.align(Alignment.Center),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Preview(showBackground = true)
@Composable
private fun SignInContentPreview() {
    Box(Modifier.fillMaxSize()) {
        SignInContent(SignInViewModel.State("Sign In"))
    }
}