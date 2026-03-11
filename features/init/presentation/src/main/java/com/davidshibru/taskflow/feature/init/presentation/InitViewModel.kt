package com.davidshibru.taskflow.feature.init.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.asContainerStateFlow
import com.davidshibru.taskflow.feature.init.domain.GetKeyFeatureUseCase
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    getKeyFeatureUseCase: GetKeyFeatureUseCase,
) : ViewModel() {

    val stateFlow: StateFlow<Container<State>> = getKeyFeatureUseCase()
        .map { keyFeature -> State(keyFeature) }
        .asContainerStateFlow(viewModelScope)


    data class State(
        val keyFeature: KeyFeature,
    )
}