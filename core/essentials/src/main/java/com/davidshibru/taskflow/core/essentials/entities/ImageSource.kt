package com.davidshibru.taskflow.core.essentials.entities

sealed interface ImageSource {

    data object Empty : ImageSource

    data class Remote(val url: String) : ImageSource

    data class Resource(val resId: Int, ): ImageSource

}