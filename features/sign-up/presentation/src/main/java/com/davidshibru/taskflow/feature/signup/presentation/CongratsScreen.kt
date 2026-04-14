package com.davidshibru.taskflow.feature.signup.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.toolbar
import com.davidshibru.taskflow.core.theme.Dimens

fun ScreenScope.congratsScreen() {

    toolbar {
        titleRes = R.string.sign_up_congratilations_title
    }

    content {
        val viewModel: CongratsViewModel = hiltViewModel()

        CongratsContent(
            onGoToSignIn = viewModel::goBackToSignIn
        )
    }
}

@Composable
private fun CongratsContent(
    onGoToSignIn: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.MediumPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.MediumSpace, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = stringResource(R.string.sign_up_congratulations),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )

        Button(
            onClick = onGoToSignIn
        ) {
            Text(stringResource(R.string.sign_up_go_to_login))
        }
    }
}