package com.davidshibru.taskflow.core.presentation

import com.davidshibru.taskflow.core.essentials.exception.ExceptionHandler
import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.core.presentation.base.ViewModelMixin
import com.davidshibru.taskflow.core.presentation.base.getMixinState
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

interface WithCommonDependencies : ViewModelMixin {

    val exceptionHandler: ExceptionHandler get() = getMixinState().exceptionHandler
    val logger: Logger get() = getMixinState().logger

    @Inject
    fun initDependencies(
        logger: Logger,
        exceptionHandler: ExceptionHandler,
    ) {
        getMixinState {
            MixinState(logger, exceptionHandler)
        }
        with(getAwaitDependenciesState()) {
            completableDeferred.complete(Unit)
        }
    }

    suspend fun awaitDependencies() = with(getAwaitDependenciesState()){
        completableDeferred.await()
    }

    private fun getMixinState() = getMixinState<MixinState>()

    private fun getAwaitDependenciesState() = getMixinState(::AwaitDependenciesState)

    private class MixinState(
        val logger: Logger,
        val exceptionHandler: ExceptionHandler,
    )

    private class AwaitDependenciesState(
        val completableDeferred: CompletableDeferred<Unit> = CompletableDeferred()
    )
}