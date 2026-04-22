package com.davidshibru.taskflow.feature.chats.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.asContainerStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val router: ChatsRouter
) : ViewModel() {

    private val vmStateFlow = MutableStateFlow(ViewModelState())

    val stateFlow: StateFlow<Container<State>> = vmStateFlow
        .map { vmState ->
            State(isLoading = vmState.isLoading)
        }
        .asContainerStateFlow(viewModelScope)

    fun onBackClicked() {
        router.navigateBack()
    }

    data class State(
        val title: String = "Chats Feature",
        val isLoading: Boolean
    )
    
    private data class ViewModelState(
        val isLoading: Boolean = false
    )
}