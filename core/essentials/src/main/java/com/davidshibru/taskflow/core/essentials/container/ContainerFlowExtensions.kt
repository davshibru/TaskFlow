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

    val loadTrigger = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    val reloadAction: () -> Unit = {
        loadTrigger.tryEmit(Unit)
    }

    return loadTrigger
        .flatMapLatest {
            this@asContainerStateFlow
                .map { value ->
                    Container.Success(value, reloadAction) as Container<T>
                }
                .onStart {
                    emit(Container.Loading)
                }
                .catch { error ->
                    emit(Container.Error(error as Exception, reloadAction))
                }
        }
        .stateIn(
            scope = scope,
            started = started,
            initialValue = Container.Loading
        )
}