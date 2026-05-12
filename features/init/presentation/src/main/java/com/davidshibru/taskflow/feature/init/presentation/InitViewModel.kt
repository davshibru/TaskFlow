package com.davidshibru.taskflow.feature.init.presentation

import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.asContainerStateFlow
import com.davidshibru.taskflow.core.essentials.container.map
import com.davidshibru.taskflow.core.presentation.WithCommonDependencies
import com.davidshibru.taskflow.core.presentation.WithMviState
import com.davidshibru.taskflow.core.presentation.base.AbstractViewModel
import com.davidshibru.taskflow.feature.init.domain.IsAuthorizedUseCase
import com.davidshibru.taskflow.feature.init.domain.ShowRandomKeyFeatureUseCase
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import com.davidshibru.taskflow.feature.init.domain.entities.ShowKeyFeatureResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    showRandomKeyFeatureUseCase: ShowRandomKeyFeatureUseCase,
    private val router: InitRouter,
    private val isAuthorizedUseCase: IsAuthorizedUseCase,
) : AbstractViewModel(),
    WithCommonDependencies,
    WithMviState<InitViewModel.State> {

    private val keyFeatureFlow = showRandomKeyFeatureUseCase.invoke()
        .transform { result ->
            when (result) {
                is ShowKeyFeatureResult.Show -> emit(result.keyFeature)
                is ShowKeyFeatureResult.Skip -> authorize()
            }
        }
        .asContainerStateFlow(viewModelScope)

    val stateFlow: StateFlow<Container<State>> = combine(
        keyFeatureFlow, progressStateFlow
    ) { featureContainer, progressState ->
        featureContainer.map { feature ->
            State(
                keyFeature = feature,
                isCheckAuthInProgress = progressState,
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), Container.Loading)

    fun letsGo() = launch(
        hideProgressPolicy = WithMviState.HideProgressPolicy.onError,
        action = ::authorize
    )

    private suspend fun authorize() {
        val isAuthorized = isAuthorizedUseCase.invoke()
        if (isAuthorized) {
            router.launchMainFlow()
        } else {
            router.launchAuthFlow()
        }

        awaitCancellation()
    }

    data class State(
        val keyFeature: KeyFeature,
        val isCheckAuthInProgress: Boolean,
    )
}