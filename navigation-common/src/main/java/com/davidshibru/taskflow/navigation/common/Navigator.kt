package com.davidshibru.taskflow.navigation.common

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.Flow

interface Navigator<T : NavKey> {
    val navigationEvents: Flow<NavigationIntent<T>>

    fun launch(route: T)
    fun restart(route: T)
    fun replace(route: T)
    fun goBack()
}
