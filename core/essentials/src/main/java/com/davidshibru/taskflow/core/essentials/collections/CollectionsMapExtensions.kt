package com.davidshibru.taskflow.core.essentials.collections

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


suspend fun <T, R: Any> Iterable<T>.mapAsync(
    mapper: suspend (T) -> R
): List<R> = mapNotNullAsync(mapper)

suspend fun <T, R: Any> Iterable<T>.mapNotNullAsync(
    mapper: suspend (T) -> R?
): List<R> = coroutineScope {
    this@mapNotNullAsync.map { item ->
        async {
            mapper(item)
        }
    }
        .awaitAll()
        .filterNotNull()
}


