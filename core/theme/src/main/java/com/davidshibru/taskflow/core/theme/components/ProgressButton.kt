package com.davidshibru.taskflow.core.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProgressButton(
    text: String,
    isInProgress: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Button(
            onClick = onClick,
            modifier = if (isInProgress) Modifier.alpha(0.0f) else Modifier
        ) {
            Text(text = text)
        }

        if (isInProgress) {
            SmallProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProgressButtonPreviewWithoutProgress() {
    ProgressButton(
        isInProgress = false,
        text = "text text",
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ProgressButtonPreviewWithProgress() {
    ProgressButton(
        isInProgress = true,
        text = "text text",
        onClick = {}
    )
}