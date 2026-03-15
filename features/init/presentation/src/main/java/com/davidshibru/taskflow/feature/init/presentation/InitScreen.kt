package com.davidshibru.taskflow.feature.init.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.entities.ImageSource
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar
import com.davidshibru.taskflow.core.theme.Dimens
import com.davidshibru.taskflow.core.theme.MediumVerticalSpace
import com.davidshibru.taskflow.core.theme.previews.PreviewScreenContent
import com.davidshibru.taskflow.core.theme.previews.ScreenPreview
import com.davidshibru.taskflow.core.theme.components.ContainerView
import com.davidshibru.taskflow.core.theme.components.ImageView
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import com.davidshibru.taskflow.feature.init.presentation.InitViewModel.State

fun ScreenScope.initScreen() {

    toolbar = ScreenToolbar.Hidden

    content {
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
}


@Composable
private fun InitScreen(
    state: State,
    onLetsGoClicked: () -> Unit
) {
    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitInitScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            state = state,
            onLetsGoClicked = onLetsGoClicked,

            )
    } else {
        LandscapeInitScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            state = state,
            onLetsGoClicked = onLetsGoClicked,
        )
    }

}

@Composable
fun PortraitInitScreen(
    modifier: Modifier = Modifier,
    state: State,
    onLetsGoClicked: () -> Unit,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
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

        ImageView(
            modifier = Modifier
                .size(Dimens.LargeImageSize),
            imageSource = keyFeature.imageSource,
        )

        MediumVerticalSpace()

        Text(
            text = keyFeature.description,
            textAlign = TextAlign.Center,
        )
        MediumVerticalSpace()

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

@Composable
fun LandscapeInitScreen(
    modifier: Modifier = Modifier,
    state: State,
    onLetsGoClicked: () -> Unit,
) {
    val keyFeature = state.keyFeature

    Row(
        modifier = modifier.fillMaxSize(), // Занимаем весь экран
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ImageView(
            modifier = Modifier
                .weight(1f)
                .wrapContentSize()
                .size(Dimens.LargeImageSize),
            imageSource = keyFeature.imageSource,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()), // Скроллим только текст, если он длинный
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
            MediumVerticalSpace()

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
}

@ScreenPreview
@Composable
private fun InitScreenPreview() = PreviewScreenContent {
    InitScreen(
        state = State(
            KeyFeature(
                id = 1,
                title = "Выбранный нами инновационный путь бодрит",
                description = "В целом, конечно, внедрение современных методик является качественно новой ступенью анализа существующих паттернов поведения.",
                imageSource = ImageSource.Empty as ImageSource,
            ),
            isCheckAuthInProgress = false
        ),
        onLetsGoClicked = {},
    )

}