package com.davidshibru.taskflow.core.navigation.base.impl

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.davidshibru.taskflow.core.essentials.dialogs.DialogConfig
import com.davidshibru.taskflow.core.essentials.dialogs.Dialogs
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class ComposeDialogs @Inject constructor() : Dialogs {

    private val dialogStateList = SnapshotStateList<DialogRecord>()
    private var idSeq: Long = 0L

    override suspend fun showAlertDialog(config: DialogConfig): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val currentId = ++idSeq
            val onDismissDialog = {
                dialogStateList.removeIf { it.id == currentId }
            }

            val record = DialogRecord(
                id = currentId,
                config = config,
                onConfirm = {
                    onDismissDialog()
                    continuation.resume(true)
                },
                onDismiss = {
                    onDismissDialog()
                    continuation.resume(false)
                }
            )

            dialogStateList.add(record)

            continuation.invokeOnCancellation {
                onDismissDialog.invoke()
            }
        }
    }

    @Composable
    fun Renderer() {
        dialogStateList.forEach { it.Renderer() }
    }

    @Composable
    private fun DialogRecord.Renderer() {
        val currentNegativeButton = config.negativeButton
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = onConfirm,
                ) {
                    Text(config.positiveButton)
                }
            },
            dismissButton = if (!currentNegativeButton.isNullOrBlank()) {
                {
                    TextButton(
                        onClick = onDismiss,
                    ) {
                        Text(currentNegativeButton)
                    }
                }
            } else {
                null
            },
            title = { Text(config.title) },
            text = { Text(config.message) },
        )
    }

    private data class DialogRecord(
        val id: Long,
        val config: DialogConfig,
        val onConfirm: () -> Unit,
        val onDismiss: () -> Unit,
    )
}