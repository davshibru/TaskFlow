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
    isInProgress: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(modifier = modifier) {
        Button(
            modifier = if (isInProgress) Modifier.alpha(0f) else Modifier,
            onClick = onClick
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
fun ProgressButtonPreviewWithOutProgress() {
    ProgressButton(
        isInProgress = false,
        text = "Test Text",
        onClick = {},
    )
}
@Preview(showBackground = true)
@Composable
fun ProgressButtonPreviewWithProgress() {
    ProgressButton(
        isInProgress = true,
        text = "Test Text",
        onClick = {},
    )
}