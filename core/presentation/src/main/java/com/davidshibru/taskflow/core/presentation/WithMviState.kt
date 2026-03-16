package com.davidshibru.taskflow.core.presentation

import com.davidshibru.taskflow.core.presentation.base.ViewModelMixin
import com.davidshibru.taskflow.core.presentation.base.getMixinState
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface WithMviState<State> : ViewModelMixin, WithCommonDependencies {

    enum class HideProgressPolicy {
        OnFinally,
        onError,
    }

    val progressStateFlow: StateFlow<Boolean> get() = getMixinState().progressStateFlow

    fun launch(
        hideProgressPolicy: HideProgressPolicy = HideProgressPolicy.OnFinally,
        action: suspend () -> Unit,
    ) {
        coroutineScope.launch {
            try {
                updateProgress(true)
                action()
            } catch (exception: Exception) {
                ensureActive()
                if (hideProgressPolicy == HideProgressPolicy.onError) updateProgress(false)
                exceptionHandler.handleException(exception)
                logger.e(exception)
            } finally {
                if (hideProgressPolicy == HideProgressPolicy.OnFinally) updateProgress(false)
            }
        }
    }

    private fun getMixinState() = getMixinState(::MixinState)

    private fun updateProgress(value: Boolean) = with(getMixinState()) {
        progressStateFlow.value = value
    }

    private class MixinState(
        val progressStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    )

}