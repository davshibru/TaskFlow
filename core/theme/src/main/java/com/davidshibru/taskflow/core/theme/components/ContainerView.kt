package com.davidshibru.taskflow.core.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.davidshibru.taskflow.core.essentials.Container
import com.davidshibru.taskflow.core.essentials.errorContainer
import com.davidshibru.taskflow.core.essentials.exceptions.ConnectionException
import com.davidshibru.taskflow.core.essentials.exceptions.mapper.ExceptionToMessageMapper
import com.davidshibru.taskflow.core.essentials.loadingContainer
import com.davidshibru.taskflow.core.essentials.successContainer
import com.davidshibru.taskflow.core.theme.Dimens
import com.davidshibru.taskflow.core.theme.MediumVerticalSpace
import com.davidshibru.taskflow.core.theme.R

@Composable
fun <T> ContainerView(
    modifier: Modifier = Modifier,
    container: Container<T>,
    onRetryAction: () -> Unit = {},
    exceptionToMessageMapper: ExceptionToMessageMapper = ExceptionToMessageMapper,
    content: @Composable (T) -> Unit,
) {
    Box(modifier) {
        container.fold(
            onLoading = {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            },
            onError = { exception ->
                val message = exceptionToMessageMapper.getLocalizedMessage(exception)
                ErrorContainerView(
                    message = message,
                    onRetryAction = onRetryAction
                )
            },
            onSuccess = { content(it) }
        )
    }
}

@Composable
fun BoxScope.ErrorContainerView(
    modifier: Modifier = Modifier,
    message: String,
    onRetryAction: () -> Unit
) {
    Column(
        modifier = modifier
            .align(Alignment.Center)
            .padding(Dimens.MediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
        )

        MediumVerticalSpace()

        Button(
            onClick = {
                onRetryAction()
            }
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SuccessContainerView() {
    ContainerView(
        container = successContainer("test test test")
    ) { value ->
        Text(text = value)
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingContainerView() {
    ContainerView<String>(
        container = loadingContainer()
    ) { value ->
        Text(text = value)
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorContainerView() {
    ContainerView<String>(
        container = errorContainer(ConnectionException())
    ) { value ->
        Text(value)
    }
}