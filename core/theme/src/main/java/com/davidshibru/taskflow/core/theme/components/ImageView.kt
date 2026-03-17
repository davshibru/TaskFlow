package com.davidshibru.taskflow.core.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.davidshibru.taskflow.core.essentials.entities.ImageSource
import com.davidshibru.taskflow.core.theme.Shapes

@Composable
fun ImageView(
    imageSource: ImageSource,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    when (imageSource) {
        is ImageSource.Remote -> RemoteImage(imageSource, contentDescription, modifier)
        is ImageSource.Empty -> EmptyImage(modifier, contentDescription)
        is ImageSource.Resource -> LocalImageView(imageSource.resId, contentDescription, modifier)
    }
}

@Composable
private fun LocalImageView(
    resId: Int,
    contentDescription: String?,
    modifier: Modifier
) {
    Image(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = Shapes.MediumRoundedCornerShape,
        ),
        painter = painterResource(resId),
        contentDescription = contentDescription,
    )
}

@Composable
private fun RemoteImage(
    imageSource: ImageSource.Remote,
    contentDescription: String?,
    modifier: Modifier
) {
    SubcomposeAsyncImage(
        model = imageSource.url,
        contentDescription = contentDescription,
        modifier = modifier,
        error = {
            EmptyImage(Modifier.matchParentSize(), contentDescription)
        }
    )
}

@Composable
private fun EmptyImage(
    modifier: Modifier,
    contentDescription: String?
) {
    Image(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = Shapes.MediumRoundedCornerShape,
        ),
        imageVector = Icons.Default.Image,
        contentDescription = contentDescription,
    )
}

@Preview(showBackground = true)
@Composable
private fun ImageViewPreview() {
    ImageView(
        imageSource = ImageSource.Empty,
        modifier = Modifier.size(100.dp)
    )
}