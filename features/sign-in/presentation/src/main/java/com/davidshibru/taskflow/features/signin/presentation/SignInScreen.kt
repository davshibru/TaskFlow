package com.davidshibru.taskflow.features.signin.presentation

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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar
import com.davidshibru.taskflow.core.theme.components.ContainerView

fun ScreenScope.signInScreen() {

    toolbar = ScreenToolbar.Default(
        title = context.getString(R.string.sign_in)
    )
    
    content {
        val viewModel: SignInViewModel = hiltViewModel()

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