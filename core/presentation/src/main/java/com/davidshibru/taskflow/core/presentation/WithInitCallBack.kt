package com.davidshibru.taskflow.core.presentation

import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.core.presentation.base.ViewModelMixin
import kotlinx.coroutines.launch
import javax.inject.Inject

interface WithInitCallBack : ViewModelMixin {

    @Inject
    fun initializeViewModel(
        logger: Logger
    ) {
        logger.d("ViewModel '${this::class.simpleName} is initialized")
        coroutineScope.launch {
            if (this@WithInitCallBack is WithCommonDependencies) {
                awaitDependencies()
            }
            onInitialized()
        }
    }

    suspend fun onInitialized()
}