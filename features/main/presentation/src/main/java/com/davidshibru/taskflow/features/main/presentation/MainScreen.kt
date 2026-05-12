package com.davidshibru.taskflow.features.main.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar
import com.davidshibru.taskflow.core.theme.Dimens
import com.davidshibru.taskflow.core.theme.components.ContainerView
import com.davidshibru.taskflow.core.theme.previews.PreviewScreenContent
import com.davidshibru.taskflow.core.theme.previews.ScreenPreview

fun ScreenScope.mainScreen() {
    toolbar = ScreenToolbar.Default(title = "Main")
    
    content {
        val viewModel: MainViewModel = hiltViewModel()
        val container: Container<MainViewModel.State> by viewModel.stateFlow.collectAsState()
    
        ContainerView(
            modifier = Modifier.fillMaxSize(),
            container = container,
        ) { state ->
            MainContent(
                state = state,
                onIncrement = viewModel::onIncrementClicked,
                onDecrement = viewModel::onDecrementClicked,
                onReset = viewModel::onResetClicked,
            )
        }
    }
}

@Composable
private fun MainContent(
    state: MainViewModel.State,
    modifier: Modifier = Modifier,
    onIncrement: () -> Unit = {},
    onDecrement: () -> Unit = {},
    onReset: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.MediumPadding),
        verticalArrangement = Arrangement.spacedBy(
            space = Dimens.MediumSpace,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = state.title,
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            text = "Counter: ${state.counter}",
            style = MaterialTheme.typography.displaySmall,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimens.SmallSpace),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                enabled = state.isDecrementEnabled,
                onClick = onDecrement,
            ) {
                Text(text = "-")
            }
            Button(onClick = onIncrement) {
                Text(text = "+")
            }
        }
        OutlinedButton(onClick = onReset) {
            Text(text = "Reset")
        }
    }
}

@ScreenPreview
@Composable
private fun MainContentPreview() = PreviewScreenContent {
    MainContent(
        state = MainViewModel.State(
            title = "Main",
            counter = 3,
            isDecrementEnabled = true,
        )
    )
}