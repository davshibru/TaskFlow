package com.davidshibru.taskflow.core.essentials.container

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
typealias ReloadAction = () -> Unit

interface ContainerScope {
    val reloadAction: ReloadAction

    fun retry() {
        reloadAction.invoke()
    }
}

sealed class Container<out T> {

    inline fun <R> fold(
        onSuccess: ContainerScope.(T) -> R,
        onError: ContainerScope.(Exception) -> R,
        onLoading: () -> R,
    ): R {
        return when(this) {
            is Error -> this.onError(exception)
            Loading -> onLoading()
            is Success<T> -> this.onSuccess(value)
        }
    }

    sealed class Completed<out T> : Container<T>(), ContainerScope {
        abstract override val reloadAction: ReloadAction
    }

    fun <T> successContainer(
        value: T,
        reloadAction: ReloadAction = {},
    ) : Success<T> {
        return Success(value, reloadAction)
    }

    fun errorContainer(
        exception: Exception,
        reloadAction: ReloadAction = {},
    ) : Error {
        return Error(exception, reloadAction)
    }

    fun loadingContainer() : Loading = Loading

    data object Loading : Container<Nothing>()

    data class Error(
        val exception: Exception,
        override val reloadAction: ReloadAction = {}
    ) : Completed<Nothing>()

    data class Success<T>(
        val value: T,
        override val reloadAction: ReloadAction = {},
    ) : Completed<T>()
}

fun <T, R> Container<T>.map(mapper: (T) -> R): Container<R> {
    return fold(
        onSuccess = { Container.Success(mapper(it), reloadAction) },
        onError = { Container.Error(it, reloadAction) },
        onLoading = { Container.Loading }
    )
}

fun <T, R> Flow<Container<T>>.containerMap(mapper: (T) -> R): Flow<Container<R>> {
    return map { container ->
        container.map(mapper)
    }
}

inline fun <T, R : Any> Container<T>.foldNullable(
    noinline onSuccess: ContainerScope.(T) -> R? = { null },
    noinline onError: ContainerScope.(Exception) -> R? = { null },
    noinline onLoading: () -> R? = { null},
): R? {
    return fold(onSuccess = onSuccess, onError = onError, onLoading = onLoading)
}

fun <T> Container<T>.getExceptionOrNull(): Exception? {
    return foldNullable(onError = { it },)
}

fun <T> Container<T>.getValueOrNull(): T? {
    return foldNullable(onSuccess = { it },)
}