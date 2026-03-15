package com.davidshibru.taskflow.core.theme.previews

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

private const val LongDimension = 800
private const val ShortDimension = 400

@Preview(showBackground = true, widthDp = ShortDimension, heightDp = LongDimension)
@Preview(showBackground = true, widthDp = LongDimension, heightDp = ShortDimension)
@Preview(
    showBackground = true,
    widthDp = ShortDimension,
    heightDp = LongDimension,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    showBackground = true,
    widthDp = LongDimension,
    heightDp = ShortDimension,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class ScreenPreview