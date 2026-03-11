package com.davidshibru.taskflow.feature.init.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.davidshibru.taskflow.core.theme.Dimens
import com.davidshibru.taskflow.core.theme.MediumVerticalSpace
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
        InitScreen(state)
    }
}

@Composable
fun InitScreen(
    state: State,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(Dimens.MediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val keyFeature = state.keyFeature
        Text(
            text = keyFeature.title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        MediumVerticalSpace()
        Text(
            text = keyFeature.description,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InitScreenPreview() {
    InitScreen(
        State(
            KeyFeature(
                id = 1,
                title = "Выбранный нами инновационный путь бодрит",
                description = "В целом, конечно, внедрение современных методик является качественно новой ступенью анализа существующих паттернов поведения.",
            )
        )
    )
}