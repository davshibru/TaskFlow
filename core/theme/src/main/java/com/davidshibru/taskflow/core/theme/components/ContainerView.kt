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
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.Container.Loading.errorContainer
import com.davidshibru.taskflow.core.essentials.container.Container.Loading.loadingContainer
import com.davidshibru.taskflow.core.essentials.container.Container.Loading.successContainer
import com.davidshibru.taskflow.core.essentials.container.ContainerScope
import com.davidshibru.taskflow.core.essentials.exception.ConnectionException
import com.davidshibru.taskflow.core.essentials.exception.mapper.ExceptionToMessageMapper
import com.davidshibru.taskflow.core.theme.Dimens
import com.davidshibru.taskflow.core.theme.MediumVerticalSpace
import com.davidshibru.taskflow.core.theme.R

@Composable
fun <T> ContainerView(
    container: Container<T>,
    modifier: Modifier = Modifier,
    exceptionToMessageMapper: ExceptionToMessageMapper = ExceptionToMessageMapper,
    content: @Composable BoxAndContainerScope.(T) -> Unit,
) {
    Box(modifier) {
        container.fold(
            onLoading = {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            },
            onError = { exception ->
                val message = exceptionToMessageMapper.getLocalizedMessage(exception)
                ErrorContainerView(message = message, onReload = reloadAction)
            },
            onSuccess = { value ->
                val combinedScope = BoxAndContainerScopeImpl(this@Box, this)
                combinedScope.content(value)
            },
        )
    }
}

@Composable
private fun BoxScope.ErrorContainerView(
    message: String,
    onReload: () -> Unit,
) {
    Column(
        modifier = Modifier
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
            onClick = { onReload.invoke() }
        ) {
            Text(text = stringResource(R.string.try_again))
        }
    }
}

interface BoxAndContainerScope : BoxScope, ContainerScope

private class BoxAndContainerScopeImpl(
    boxScope: BoxScope,
    containerScope: ContainerScope,
) : BoxAndContainerScope, BoxScope by boxScope, ContainerScope by containerScope


@Preview(showBackground = true)
@Composable
private fun SuccessContainerView() {
    ContainerView(
        container = successContainer("Test test test")
    ) { value ->
        Text(value)
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingContainerView() {
    ContainerView<String>(
        container = loadingContainer()
    ) { value ->
        Text(value)
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