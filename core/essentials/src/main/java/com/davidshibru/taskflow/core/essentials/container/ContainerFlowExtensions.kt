package com.davidshibru.taskflow.core.essentials.container

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.asContainerStateFlow(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(5000L)
): StateFlow<Container<T>> {

    val loadTrigger = MutableSharedFlow<Boolean>(replay = 1).apply {
        tryEmit(false)
    }

    val reloadAction: ReloadAction = { silently ->
        loadTrigger.tryEmit(silently)
    }

    var lastCompletedContainer: Container.Completed<T>? = null

    return loadTrigger
        .flatMapLatest { silently ->
            this@asContainerStateFlow
                .map { value ->
                    val container = Container.Success(value, reloadAction)
                    lastCompletedContainer = container
                    container as Container<T>
                }
                .onStart {
                    val currentContainer = lastCompletedContainer
                    if (silently && currentContainer != null) {
                        emit(currentContainer.withLoading(isLoading = true))
                    } else {
                        emit(Container.Loading)
                    }
                }
                .catch { error ->
                    val container = Container.Error(error as Exception, reloadAction)
                    lastCompletedContainer = container
                    emit(container)
                }
        }
        .stateIn(
            scope = scope,
            started = started,
            initialValue = Container.Loading
        )
}
