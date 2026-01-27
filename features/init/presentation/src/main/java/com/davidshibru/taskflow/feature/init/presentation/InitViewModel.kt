package com.davidshibru.taskflow.feature.init.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.Container
import com.davidshibru.taskflow.core.essentials.exceptions.handler.ExceptionHandler
import com.davidshibru.taskflow.core.essentials.map
import com.davidshibru.taskflow.feature.init.domain.GetKeyFeatureUseCase
import com.davidshibru.taskflow.feature.init.domain.IsAuthorizedUseCase
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    getKeyFeatureUseCase: GetKeyFeatureUseCase,
    private val isAuthorizedUseCase: IsAuthorizedUseCase,
    private val router: InitRouter,
    private val exceptionHandler: ExceptionHandler,
) : ViewModel() {

    private val vmStateFlow = MutableStateFlow(ViewModelState())

    val stateFlow: StateFlow<Container<State>> = combine(
        getKeyFeatureUseCase.invoke(),
        vmStateFlow,
    ) { container, vmState ->
        container.map { keyFeature ->
            State(keyFeature, vmState.isCheckAuthInProgress)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), Container.Loading)

    fun letsGo() {
        viewModelScope.launch {
            try {
                showProgress()
                val isAuthorized = isAuthorizedUseCase.invoke()
                if (isAuthorized) {
                    // main flow
                } else {
                    router.launchSignInScreen()
                }
            } catch (e: Exception) {
                ensureActive()
                hideProgress()
                exceptionHandler.handlerException(e)
            }
        }
    }

    private fun showProgress() {
        vmStateFlow.update { it.copy(isCheckAuthInProgress = true) }
    }
    private fun hideProgress() {
        vmStateFlow.update { it.copy(isCheckAuthInProgress = false) }
    }

    data class State(
        val keyFeature: KeyFeature,
        val isCheckAuthInProgress: Boolean,
    )

    private data class ViewModelState(
        val isCheckAuthInProgress: Boolean = false,
    )
}