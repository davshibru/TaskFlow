package com.davidshibru.taskflow.core.essentials.container

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

interface LazyFlowSubject<T> {
    fun listenReloadable(
        emitReloadFunction: Boolean = true,
        emitBackgroundLoads: Boolean = true,
    ): Flow<Container<T>>

    companion object {
        fun <T> create(
            loader: suspend LazyFlowSubjectScope<T>.() -> Unit,
        ): LazyFlowSubject<T> = DefaultSubjectFactory().create(loader)
    }
}

interface SubjectFactory {
    fun <T> create(
        loader: suspend LazyFlowSubjectScope<T>.() -> Unit,
    ): LazyFlowSubject<T>
}

class DefaultSubjectFactory(
    private val cacheTimeoutMillis: Long = DEFAULT_CACHE_TIMEOUT_MILLIS,
) : SubjectFactory {
    override fun <T> create(
        loader: suspend LazyFlowSubjectScope<T>.() -> Unit,
    ): LazyFlowSubject<T> = DefaultLazyFlowSubject(loader, cacheTimeoutMillis)

    companion object {
        const val DEFAULT_CACHE_TIMEOUT_MILLIS = 5000L
    }
}

private class DefaultLazyFlowSubject<T>(
    private val loader: suspend LazyFlowSubjectScope<T>.() -> Unit,
    private val cacheTimeoutMillis: Long,
) : LazyFlowSubject<T> {
    private val loadTrigger = MutableSharedFlow<Boolean>(replay = 1).apply {
        tryEmit(false)
    }
    private var lastCompletedContainer: Container.Completed<T>? = null
    private var lastCompletedTimeMillis: Long = 0L

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun listenReloadable(
        emitReloadFunction: Boolean,
        emitBackgroundLoads: Boolean,
    ): Flow<Container<T>> {
        val reloadAction: ReloadAction = if (emitReloadFunction) {
            { silently -> loadTrigger.tryEmit(silently) }
        } else {
            { _ -> }
        }

        return loadTrigger
            .flatMapLatest { silently ->
                flow {
                    val lazyFlowSubjectScope = LazyFlowSubjectScopeImpl(
                        collector = this,
                        reloadAction = reloadAction,
                        onContainerCreated = { container ->
                            lastCompletedContainer = container
                            lastCompletedTimeMillis = System.currentTimeMillis()
                        }
                    )

                    lazyFlowSubjectScope.loader()
                }.onStart {
                    val currentContainer = lastCompletedContainer
                    if (
                        currentContainer != null &&
                        emitBackgroundLoads &&
                        (silently || isCacheValid())
                    ) {
                        emit(currentContainer.withLoading(isLoading = true))
                    } else {
                        emit(Container.Loading)
                    }
                }.catch { error ->
                    val container = Container.Error(error as Exception, reloadAction)
                    lastCompletedContainer = container
                    lastCompletedTimeMillis = System.currentTimeMillis()
                    emit(container)
                }
            }
    }

    private fun isCacheValid(): Boolean {
        return System.currentTimeMillis() - lastCompletedTimeMillis <= cacheTimeoutMillis
    }
}

interface LazyFlowSubjectScope<T> {
    suspend fun emit(value: T)
}

private class LazyFlowSubjectScopeImpl<T>(
    private val collector: FlowCollector<Container<T>>,
    private val reloadAction: ReloadAction,
    private val onContainerCreated: (Container.Completed<T>) -> Unit,
) : LazyFlowSubjectScope<T> {
    override suspend fun emit(value: T) {
        val container = Container.Success(value, reloadAction)
        onContainerCreated(container)
        collector.emit(container)
    }
}
