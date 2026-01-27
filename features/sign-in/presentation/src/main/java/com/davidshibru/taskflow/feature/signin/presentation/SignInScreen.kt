package com.davidshibru.taskflow.feature.signin.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidshibru.taskflow.core.essentials.Container
import com.davidshibru.taskflow.core.theme.components.ContainerView

@Composable
fun SignInScreen() {
    val viewModel: SignInViewModel = hiltViewModel()
    val container: Container<SignInViewModel.State> by viewModel.stateFlow.collectAsState()
    ContainerView(
        container = container,
        modifier = Modifier.fillMaxSize(),
    ) { state ->
        Box(modifier = Modifier.fillMaxSize()) {
            SignInContent(
                state = state,
            )
        }
    }
}

@Composable
fun BoxScope.SignInContent(
    state: SignInViewModel.State,
) {
    Text(
        text = "${state.title} Screen",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.align(Alignment.Center),
        textAlign = TextAlign.Center,
    )
}

@Preview(showBackground = true)
@Composable
fun SignInContentPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        SignInContent(
            state = SignInViewModel.State(),
        )
    }
}