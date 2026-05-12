package com.davidshibru.taskflow.features.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.asContainerStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val router: MainRouter
) : ViewModel() {

    private val vmStateFlow = MutableStateFlow(ViewModelState())

    val stateFlow: StateFlow<Container<State>> = vmStateFlow
        .map { vmState ->
            State(
                title = "Main",
                counter = vmState.counter,
                isDecrementEnabled = vmState.counter > 0,
            )
        }
        .asContainerStateFlow(viewModelScope)

    fun onIncrementClicked() {
        vmStateFlow.update { state ->
            state.copy(counter = state.counter + 1)
        }
    }

    fun onDecrementClicked() {
        vmStateFlow.update { state ->
            state.copy(counter = (state.counter - 1).coerceAtLeast(0))
        }
    }

    fun onResetClicked() {
        vmStateFlow.update { state ->
            state.copy(counter = 0)
        }
    }

    fun onBackClicked() {
        router.navigateBack()
    }

    data class State(
        val title: String,
        val counter: Int,
        val isDecrementEnabled: Boolean,
    )
    
    private data class ViewModelState(
        val counter: Int = 0,
    )
}