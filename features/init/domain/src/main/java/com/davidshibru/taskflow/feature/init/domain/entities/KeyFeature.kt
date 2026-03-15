package com.davidshibru.taskflow.feature.init.domain.entities

import com.davidshibru.taskflow.core.essentials.entities.ImageSource
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

data class KeyFeature(
    val id: Long,
    val title: String,
    val description: String,
    val imageSource: ImageSource,
    internal val lastDisplayTime: ZonedDateTime = ZonedDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC),
)