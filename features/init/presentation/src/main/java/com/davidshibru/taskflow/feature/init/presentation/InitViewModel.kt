package com.davidshibru.taskflow.feature.init.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.Container
import com.davidshibru.taskflow.core.essentials.containerMap
import com.davidshibru.taskflow.core.essentials.exceptions.mapper.ExceptionToMessageMapper
import com.davidshibru.taskflow.feature.init.domain.GetKeyFeatureUseCase
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    private val getKeyFeatureUseCase: GetKeyFeatureUseCase,
    private val exceptionToMessageMapper: ExceptionToMessageMapper
) : ViewModel() {

    val stateFlow: StateFlow<Container<State>> = getKeyFeatureUseCase
        .invoke()
        .containerMap { State(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), Container.Loading)

    data class State(
        val keyFeature: KeyFeature,
    )
}