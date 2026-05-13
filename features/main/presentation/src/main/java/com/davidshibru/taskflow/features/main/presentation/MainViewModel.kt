package com.davidshibru.taskflow.features.main.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.asContainerStateFlow
import com.davidshibru.taskflow.core.presentation.base.AbstractViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : AbstractViewModel() {

    private val _currentIndexFlow = savedStateHandle.getMutableStateFlow(
        key = "currentIndex",
        initialValue = 0,
    )

    val currentIndexFlow: StateFlow<Int> = _currentIndexFlow

    fun setCurrentIndex(index: Int) {
        _currentIndexFlow.value = index
    }

}