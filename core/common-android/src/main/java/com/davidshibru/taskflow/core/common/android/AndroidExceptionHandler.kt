package com.davidshibru.taskflow.core.common.android

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import com.davidshibru.taskflow.core.essentials.exception.ExceptionHandler
import com.davidshibru.taskflow.core.essentials.exception.mapper.ExceptionToMessageMapper
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class AndroidExceptionHandler @Inject constructor(
    private val exceptionToMessageMapper: ExceptionToMessageMapper,
) : ExceptionHandler {

    private val errorMessageState = mutableStateOf<String?>(null)

    override fun handleException(exception: Exception) {
        val message = exceptionToMessageMapper.getLocalizedMessage(exception)
        errorMessageState.value = message
    }

    @Composable
    fun ErrorDialog() {
        errorMessageState.value?.let { message ->
            AlertDialog(
                onDismissRequest = { errorMessageState.value = null },
                confirmButton = {
                    TextButton(
                        onClick = { errorMessageState.value = null }
                    ) {
                        Text(text = stringResource(R.string.ok))
                    }
                },
                title = {
                    Text(text = stringResource(R.string.error))
                },
                text = {
                    Text(message)
                }
            )
        }
    }
}