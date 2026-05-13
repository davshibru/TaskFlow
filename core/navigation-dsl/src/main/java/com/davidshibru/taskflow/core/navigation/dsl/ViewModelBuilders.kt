package com.davidshibru.taskflow.core.navigation.dsl

import androidx.lifecycle.ViewModel

inline fun <reified T : ViewModel> ScreenScope.viewModel(): T {
    return viewModel(T::class)
}

inline fun <reified T : ViewModel, F> ScreenScope.viewModel(
    noinline callback: F.() -> T,
): T {
    return viewModel(T::class, callback)
}
