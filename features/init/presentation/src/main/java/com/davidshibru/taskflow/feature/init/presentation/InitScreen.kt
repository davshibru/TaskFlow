package com.davidshibru.taskflow.feature.init.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.davidshibru.taskflow.core.essentials.Container
import com.davidshibru.taskflow.feature.init.presentation.InitViewModel.State

@Composable
fun InitScreen() {
    val viewModel: InitViewModel = hiltViewModel()
    val state: Container<State> by viewModel.stateFlow.collectAsState()
    InitScreen(
        state = state
    )
}

@Composable
fun <T>InitScreen(
    modifier: Modifier = Modifier,
    state: Container<T>,
) {

}