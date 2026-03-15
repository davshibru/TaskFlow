package com.davidshibru.taskflow.feature.init.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.asContainerStateFlow
import com.davidshibru.taskflow.core.essentials.container.map
import com.davidshibru.taskflow.core.essentials.exception.ExceptionHandler
import com.davidshibru.taskflow.feature.init.domain.IsAuthorizedUseCase
import com.davidshibru.taskflow.feature.init.domain.ShowRandomKeyFeatureUseCase
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import com.davidshibru.taskflow.feature.init.domain.entities.ShowKeyFeatureResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    showRandomKeyFeatureUseCase: ShowRandomKeyFeatureUseCase,
    private val router: InitRouter,
    private val isAuthorizedUseCase: IsAuthorizedUseCase,
    private val exceptionHandler: ExceptionHandler,
) : ViewModel() {

    private val vmStateFlow = MutableStateFlow(ViewModelState())

    private val keyFeatureFlow = showRandomKeyFeatureUseCase.invoke()
        .transform<ShowKeyFeatureResult, KeyFeature> { result ->
            when (result) {
                is ShowKeyFeatureResult.Show -> result.keyFeature
                is ShowKeyFeatureResult.Skip -> authorize()
            }
        }
        .asContainerStateFlow(viewModelScope)

    val stateFlow: StateFlow<Container<State>> = combine(
        keyFeatureFlow, vmStateFlow
    ) { featureContainer, vmState ->
        featureContainer.map { feature ->
            State(
                keyFeature = feature,
                isCheckAuthInProgress = vmState.isCheckAuthInProgress,
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), Container.Loading)

    fun letsGo() {
        viewModelScope.launch {
            try {
                showProgress()
                authorize()
            } catch (e: Exception) {
                ensureActive()
                exceptionHandler.handleException(e)
            } finally {
                hideProgress()
            }
        }
    }

    private suspend fun authorize() {
        val isAuthorized = isAuthorizedUseCase.invoke()
        if (isAuthorized) {
            // router.navigateToMain()
        } else {
            router.launchSignIn()
        }

        awaitCancellation()
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