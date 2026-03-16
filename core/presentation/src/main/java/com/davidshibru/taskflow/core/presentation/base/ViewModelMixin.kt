package com.davidshibru.taskflow.core.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

interface ViewModelMixin {

    val coroutineScope: CoroutineScope

    fun <T : Any> getMixinState(
        stateClass: KClass<T>,
        initializer: () -> T,
    ): T

}

inline fun <reified T: Any> ViewModelMixin.getMixinState(
    noinline initializer: () -> T = {
        T::class.java.getDeclaredConstructor().newInstance()
    },
):T {
    return getMixinState(T::class, initializer)
}