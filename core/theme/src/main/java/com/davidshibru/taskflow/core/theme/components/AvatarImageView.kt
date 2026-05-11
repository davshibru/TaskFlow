package com.davidshibru.taskflow.core.theme.components

import android.widget.ImageView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.davidshibru.taskflow.core.essentials.entities.ImageSource
import com.davidshibru.taskflow.core.theme.Dimens
import kotlin.math.max
import kotlin.random.Random

@Composable
fun AvatarImageView(
    modifier: Modifier,
    name: String,
    imageSource: ImageSource,
) {
    if (imageSource is ImageSource.Empty) {
        FirstLaterAvatar(modifier = modifier, name = name)
    } else {
        ImageView(
            imageSource = imageSource,
            modifier = modifier,
            contentDescription = "Avatar of $name"
        )
    }
}

@Composable
private fun FirstLaterAvatar(
    modifier: Modifier,
    name: String,
) {
    val firstLetter = name.firstOrNull()?.uppercase() ?: "?"
    val isDarkMode = isSystemInDarkTheme()
    val color = Random(name.hashCode()).run {
        val nextColor: () -> Int = if (isDarkMode) ::nextLight else ::nextDark
        Color(nextColor(), nextColor(), nextColor())
    }
    val textMeasure = rememberTextMeasurer()
    val textLayoutResult = textMeasure.measure(firstLetter)

    Canvas(
        modifier = modifier
            .background(
                color = color,
                shape = CircleShape,
            )
    ) {
        val containerMinDimens = size.minDimension
        val textMaxDimen = textLayoutResult.size.run { max(width, height) }
        val scaleFactor = containerMinDimens / textMaxDimen

        scale(
            scale = scaleFactor * 0.7f
        ) {
            drawText(
                textLayoutResult = textLayoutResult,
                color = if (isDarkMode) Color.Black else Color.White,
                topLeft = Offset(
                    x = size.center.x - textLayoutResult.size.width / 2,
                    y = size.center.y - textLayoutResult.size.height / 2,
                )
            )
        }
    }
}

private fun Random.nextDark(): Int {
    return nextInt(128)
}

private fun Random.nextLight(): Int {
    return nextInt(128, 256)
}

@Composable
@Preview(showBackground = true)
private fun FirstLetterAvatarPreview() {
    FirstLaterAvatar(
        modifier = Modifier.size(Dimens.MediumAvatarSize),
        name = "Jo",
    )
}