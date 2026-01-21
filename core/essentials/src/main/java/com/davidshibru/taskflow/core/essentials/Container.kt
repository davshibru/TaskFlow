package com.davidshibru.taskflow.core.essentials

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class Container<out T> {


    abstract fun <R> fold(
        onSuccess: (T) -> R,
        onError: (Exception) -> R,
        onLoading: () -> R,
    ): R

    data object Loading : Container<Nothing>() {
        override fun <R> fold(
            onSuccess: (Nothing) -> R,
            onError: (Exception) -> R,
            onLoading: () -> R
        ): R = onLoading()
    }

    data class Error(
        val exception: Exception
    ) : Container<Nothing>() {
        override fun <R> fold(
            onSuccess: (Nothing) -> R,
            onError: (Exception) -> R,
            onLoading: () -> R
        ): R = onError(exception)
    }

    data class Success<T>(
        val value: T,
    ) : Container<T>() {
        override fun <R> fold(
            onSuccess: (T) -> R,
            onError: (Exception) -> R,
            onLoading: () -> R
        ): R = onSuccess(value)
    }
}

fun <T, R> Container<T>.map(
    mapper: (T) -> R
): Container<R> {
    return fold(
        onSuccess = { Container.Success(mapper(it)) },
        onError = { Container.Error(it) },
        onLoading = { Container.Loading }
    )
}

fun <T, R> Flow<Container<T>>.containerMap(
    mapper: (T) -> R
): Flow<Container<R>> {
    return map { container ->
        container.map(mapper)
    }
}

fun <T, R> Container<T>.foldNullable(
    onSuccess: (T) -> R? = { null },
    onError: (Exception) -> R? = { null },
    onLoading: () -> R? = { null },
): R? {
    return fold(onSuccess, onError, onLoading)
}

fun <T> Container<T>.getExceptionOrNull() : Exception? {
    return foldNullable(onError = { it })
}

fun <T> Container<T>.getValueOrNull(): T? {
    return foldNullable(onSuccess = { it })
}

