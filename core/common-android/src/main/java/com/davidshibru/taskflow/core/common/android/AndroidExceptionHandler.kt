package com.davidshibru.taskflow.core.common.android

import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import com.davidshibru.taskflow.core.essentials.exceptions.handler.ExceptionHandler
import com.davidshibru.taskflow.core.essentials.exceptions.mapper.ExceptionToMessageMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class AndroidExceptionHandler @Inject constructor(
    private val exceptionToMessageMapper: ExceptionToMessageMapper,
    @ApplicationContext private val context: Context,
) : ExceptionHandler {

    private val errorMessageState = mutableStateOf<String?>(null)


    override fun handlerException(exception: Exception) {
        val message = exceptionToMessageMapper.getLocalizedMessage(exception)
        errorMessageState.value = message
    }

    @Composable
    fun ErrorDialog() {
        errorMessageState.value?.let { message ->
            AlertDialog(
                onDismissRequest = { errorMessageState.value = null},
                confirmButton = {
                    TextButton(
                        onClick = { errorMessageState.value = null },
                    ) {
                        Text(stringResource(R.string.ok))
                    }
                },
                title = {
                    Text(stringResource(R.string.error))
                },
                text = {
                    Text(message)
                },
            )
        }
    }
}