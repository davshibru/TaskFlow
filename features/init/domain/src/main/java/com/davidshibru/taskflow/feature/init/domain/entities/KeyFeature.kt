package com.davidshibru.taskflow.feature.init.domain.entities

import com.davidshibru.taskflow.core.essentials.entities.ImageSource

data class KeyFeature(
    val id: Long,
    val title: String,
    val description: String,
    val imageSource: ImageSource,
)