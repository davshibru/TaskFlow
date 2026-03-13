package com.davidshibru.taskflow.feature.init.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.theme.components.ContainerView
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import com.davidshibru.taskflow.feature.init.presentation.InitViewModel.State

@Composable
fun InitScreen() {
    val viewModel: InitViewModel = hiltViewModel()
    val container: Container<State> by viewModel.stateFlow.collectAsState()

    ContainerView(
        container = container,
        modifier = Modifier.fillMaxSize()
    ) { state ->
        InitScreen(
            state = state,
            onLetsGoClicked = viewModel::letsGo
        )
    }
}

@Composable
private fun InitScreen(
    state: State,
    onLetsGoClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val keyFeature = state.keyFeature
        Text(
            text = keyFeature.title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = keyFeature.description,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onLetsGoClicked,
            enabled = !state.isCheckAuthInProgress
        ) {
            if (state.isCheckAuthInProgress) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(text = stringResource(R.string.let_s_go))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InitScreenPreview() {
    InitScreen(
        state = State(
            KeyFeature(
                id = 1,
                title = "Выбранный нами инновационный путь бодрит",
                description = "В целом, конечно, внедрение современных методик является качественно новой ступенью анализа существующих паттернов поведения.",
            ),
            isCheckAuthInProgress = false
        ),
        onLetsGoClicked = {},
    )
}