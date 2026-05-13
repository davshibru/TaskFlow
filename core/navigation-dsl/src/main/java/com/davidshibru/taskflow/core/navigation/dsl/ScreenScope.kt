package com.davidshibru.taskflow.core.navigation.dsl

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

interface ScreenScope : ConfiguredScreen {

    val context: Context

    val savedStateHandle: SavedStateHandle

    val coroutineScope: CoroutineScope

    override var toolbar: ScreenToolbar

    override var navigationBar: ScreenNavigationBar

    override var backHandler: ScreenBackHandler

    fun content(block: @Composable () -> Unit)

    fun <T : ViewModel> viewModel(vmClass: KClass<T>): T

    fun <T : ViewModel, F> viewModel(
        vmClass: KClass<T>,
        callback: F.() -> T
    ): T

    fun applyConfiguration(
        route: BaseRoute,
        scope: ScreenScope = this
    )
}