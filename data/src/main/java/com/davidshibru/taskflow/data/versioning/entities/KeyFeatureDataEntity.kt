package com.davidshibru.taskflow.data.versioning.entities

import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.core.essentials.entities.ImageSource

data class KeyFeatureDataEntity(
    val id: Id,
    val title: String,
    val description: String,
    val imageSource: ImageSource
)