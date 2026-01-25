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
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidshibru.taskflow.core.essentials.Container
import com.davidshibru.taskflow.core.theme.Dimens
import com.davidshibru.taskflow.core.theme.MediumVerticalSpace
import com.davidshibru.taskflow.core.theme.components.ContainerView
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature

@Composable
fun InitScreen() {
    val viewModel: InitViewModel = hiltViewModel()
    val container: Container<InitViewModel.State> by viewModel.stateFlow.collectAsState()
    ContainerView(
        container = container,
        modifier = Modifier.fillMaxSize(),
    ) { state ->
        InitContent(state)
    }
}

@Composable
fun InitContent(
    state: InitViewModel.State,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(Dimens.MediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val keyFeature: KeyFeature = state.keyFeature

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
fun InitContentPreview() {
    InitContent(
        state = InitViewModel.State(
            keyFeature = KeyFeature(
                id = 1,
                title = "This is a title",
                description = "Tgds fgd gtt asdo kfodgk gpkth hgffop dfgje asdg vcxjf sdfafsdg gtgtgtl fdlgk",

            )
        )
    )
}