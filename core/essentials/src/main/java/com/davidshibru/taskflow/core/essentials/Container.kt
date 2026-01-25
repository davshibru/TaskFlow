package com.davidshibru.taskflow.core.essentials

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class Container<out T> {


    public inline fun <R> fold(
        onSuccess: (T) -> R,
        onError: (Exception) -> R,
        onLoading: () -> R,
    ): R {
        return when (this) {
            is Success<T> -> onSuccess(value)
            Loading -> onLoading()
            is Error -> onError(exception)
        }
    }

    data object Loading : Container<Nothing>()

    data class Error(
        val exception: Exception
    ) : Container<Nothing>()

    data class Success<T>(
        val value: T,
    ) : Container<T>()
}

fun <T> successContainer(
    value: T,
): Container.Success<T> {
    @Suppress("DEPRECATION")
    return Container.Success(value)
}

fun loadingContainer(): Container.Loading {
    @Suppress("DEPRECATION")
    return Container.Loading
}

fun errorContainer(
    exception: Exception,
) : Container.Error {
    @Suppress("DEPRECATION")
    return Container.Error(exception)
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

fun <T> Container<T>.getExceptionOrNull(): Exception? {
    return foldNullable(onError = { it })
}

fun <T> Container<T>.getValueOrNull(): T? {
    return foldNullable(onSuccess = { it })
}

