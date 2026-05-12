package com.davidshibru.taskflow.features.main.domain.entities

import com.davidshibru.taskflow.core.essentials.entities.Id

data class MainEntity(
    val id: Id = Id.Empty,
    val title: String = "",
)