package com.davidshibru.taskflow.core.essentials.container

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.reflect.KClass

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

    companion object {
        fun <T> success(
            value: T,
            reloadAction: ReloadAction = {},
        ) : Success<T> {
            return Success(value, reloadAction)
        }

        fun error(
            exception: Exception,
            reloadAction: ReloadAction = {},
        ) : Error {
            return Error(exception, reloadAction)
        }

        fun loading() : Loading = Loading
    }

    fun unwrap(): T {
        return when (this) {
            is Success -> value
            is Error -> throw exception
            Loading -> throw IllegalStateException("Cannot unwrap Loading container")
        }
    }

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

fun <T, R> Container.Completed<T>.map(mapper: (T) -> R): Container.Completed<R> {
    return when(this) {
        is Container.Error -> { Container.Error(this.exception, reloadAction) }
        is Container.Success<T> -> { Container.Success(mapper(value), reloadAction) }
    }
}

/**
 * Maps the exception of the container if it matches the [exceptionClass].
 */
inline fun <T, E : Exception> Container<T>.mapException(
    exceptionClass: KClass<E>,
    crossinline mapper: (E) -> Exception
): Container<T> {
    val current = this
    return if (current is Container.Error && exceptionClass.isInstance(current.exception)) {
        try {
            @Suppress("UNCHECKED_CAST")
            val mappedException = mapper(current.exception as E)
            Container.Error(mappedException, current.reloadAction)
        } catch (e: Exception) {
            Container.Error(e, current.reloadAction)
        }
    } else {
        this
    }
}

inline fun <T, E : Exception> Container.Completed<T>.catch(
    exceptionClass: KClass<E>,
    crossinline mapper: (E) -> Container.Completed<T>
): Container.Completed<T> {
    val current = this

    return if (current is Container.Error && exceptionClass.isInstance(current.exception)) {
        try {
            @Suppress("UNCHECKED_CAST")
            mapper(current.exception as E)
        } catch (e: Exception) {
            Container.Error(e, current.reloadAction)
        }
    } else {
        this
    }
}

inline fun <T, reified E : Exception> Container.Completed<T>.catch(
    crossinline mapper: (E) -> Container.Completed<T>
): Container.Completed<T> {
    return catch(E::class, mapper)
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
