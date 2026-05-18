package com.davidshibru.taskflow.features.profile.domain.entities

import com.davidshibru.taskflow.core.essentials.entities.Id

data class ProfileEntity(
    val id: Id = Id.Empty,
    val title: String = "",
)