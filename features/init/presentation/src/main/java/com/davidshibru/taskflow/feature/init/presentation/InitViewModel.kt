package com.davidshibru.taskflow.feature.init.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.asContainerStateFlow
import com.davidshibru.taskflow.core.essentials.exception.ExceptionHandler
import com.davidshibru.taskflow.feature.init.domain.GetKeyFeatureUseCase
import com.davidshibru.taskflow.feature.init.domain.IsAuthorizedUseCase
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    getKeyFeatureUseCase: GetKeyFeatureUseCase,
    private val router: InitRouter,
    private val isAuthorizedUseCase: IsAuthorizedUseCase,
    private val exceptionHandler: ExceptionHandler,
) : ViewModel() {

    private val vmStateFlow = MutableStateFlow(ViewModelState())

    val stateFlow: StateFlow<Container<State>> = combine(
        getKeyFeatureUseCase(), vmStateFlow
    ) { keyFeature, vmState ->
        State(
            keyFeature = keyFeature,
            isCheckAuthInProgress = vmState.isCheckAuthInProgress,
        )
    }.asContainerStateFlow(viewModelScope)

    fun letsGo() {
        viewModelScope.launch {
            showProgress()
            try {
                kotlinx.coroutines.delay(1000)
                val isAuthorized = isAuthorizedUseCase.invoke()

                if (isAuthorized) {
                    // router.navigateToMain()
                } else {
                    router.launchSignIn()
                }
            } catch (e: Exception) {
                ensureActive()
                hideProgress()
                exceptionHandler.handleException(e)
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