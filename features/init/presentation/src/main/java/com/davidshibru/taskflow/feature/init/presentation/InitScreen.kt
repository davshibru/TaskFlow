package com.davidshibru.taskflow.feature.init.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun InitScreen() {
    val viewModel: InitViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsState()
}

@Composable
fun InitScreen(
    state: InitViewModel.State,
) {

}