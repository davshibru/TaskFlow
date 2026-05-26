package com.davidshibru.taskflow.feature.chats.presentation

import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.containerMap
import com.davidshibru.taskflow.core.essentials.container.map
import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.core.presentation.WithMviState
import com.davidshibru.taskflow.core.presentation.base.AbstractViewModel
import com.davidshibru.taskflow.feature.chats.domain.DeleteChatUseCase
import com.davidshibru.taskflow.feature.chats.domain.GetChatsUseCase
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import com.davidshibru.taskflow.feature.chats.domain.entities.ChatId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val router: ChatsRouter,
    getChatsUseCase: GetChatsUseCase,
    private val deleteChatsUseCase: DeleteChatUseCase,
) : AbstractViewModel(),
    WithMviState<ChatsViewModel.State> {

    private val reducer = getChatsUseCase.invoke()
        .containerMap { it.toImmutableList() }

    private val _stateFlow = MutableStateFlow(StateImpl())
    val stateFlow: StateFlow<Container<State>> = combine(_stateFlow, reducer) { state, chats ->
        chats.map { state.copy(originChats = it) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Container.Loading,
    )

    fun executeAction(action: ChatsAction) = when (action) {
        is ChatsAction.DeleteChat -> deleteChat(chatId = action.chatId)
    }

    private fun deleteChat(chatId: ChatId) = launch {
        try {
            disableChat(chatId = chatId)
            deleteChatsUseCase.invoke(chatId = chatId)
        } finally {
            enableChat(chatId = chatId)
        }
    }

    private fun enableChat(chatId: Id) = _stateFlow.update { currentState ->
        currentState.copy(disabledChatIds = currentState.disabledChatIds - chatId)
    }

    private fun disableChat(chatId: Id) = _stateFlow.update { currentState ->
        currentState.copy(disabledChatIds = currentState.disabledChatIds + chatId)
    }

    interface State {
        val chats: ImmutableList<UiChat>
    }

    private data class StateImpl(
        private val originChats: List<Chat> = persistentListOf(),
        val disabledChatIds: Set<Id> = emptySet(),
    ) : State {
        override val chats: ImmutableList<UiChat> = originChats
            .map {
                UiChat(
                    id = it.id,
                    title = it.title,
                    imageSource = it.imageSource,
                    lastMessage = it.lastMessage,
                    unreadMessageCount = it.unreadMessageCount,
                    isEnabled = !disabledChatIds.contains(it.id),
                )
            }
            .toImmutableList()
    }
}
