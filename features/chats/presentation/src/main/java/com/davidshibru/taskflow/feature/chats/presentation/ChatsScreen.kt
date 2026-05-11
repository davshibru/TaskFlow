package com.davidshibru.taskflow.feature.chats.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar
import com.davidshibru.taskflow.core.theme.Dimens
import com.davidshibru.taskflow.core.theme.components.AvatarImageView
import com.davidshibru.taskflow.core.theme.components.ContainerView
import com.davidshibru.taskflow.core.theme.components.ImageView
import com.davidshibru.taskflow.core.theme.previews.PreviewScreenContent
import com.davidshibru.taskflow.core.theme.previews.ScreenPreview
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import com.davidshibru.taskflow.feature.chats.domain.entities.hasUnreadMessages
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlin.math.max

fun ScreenScope.chatsScreen() {
    toolbar = ScreenToolbar.Default(
        titleRes = R.string.chats_title
    )

    content {
        val viewModel: ChatsViewModel = hiltViewModel()
        val container: Container<ChatsViewModel.State> by viewModel.stateFlow.collectAsState()

        ContainerView(
            modifier = Modifier.fillMaxSize(),
            enablePullToRefresh = true,
            container = container,
        ) { state ->
            ChatsContent(
                state = state,
                onAction = viewModel::executeAction,
            )
        }
    }
}

@Composable
private fun BoxScope.ChatsContent(
    state: ChatsViewModel.State,
    onAction: (ChatsAction) -> Unit = {},
) {
    if (state.chats.isNotEmpty()) {
        ChatsList(
            chats = state.chats,
            onDeleteChat = { onAction(ChatsAction.DeleteChat(it)) }
        )
    } else {
        EmptyList()
    }
}

@Composable
private fun ChatsList(
    modifier: Modifier = Modifier,
    chats: ImmutableList<UiChat>,
    onDeleteChat: (chatId: Id) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(
            items = chats,
            key = { it.id.value }
        ) { chat ->
            ChatItem(
                modifier = Modifier.animateItem(),
                chat = chat,
                onDeleteChat = { onDeleteChat(chat.id) }
            )
        }
    }
}

@Composable
private fun ChatItem(
    modifier: Modifier = Modifier,
    chat: UiChat,
    onDeleteChat: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .unreadMessagesBackground(chat.hasUnreadMessages)
            .alpha(if (chat.isEnabled) 1f else 0.5f)
            .padding(
                vertical = Dimens.ExtraSmallPadding,
                horizontal = Dimens.SmallPadding,
            ),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SmallSpace),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AvatarImageView(
            modifier = Modifier.size(Dimens.MediumImageSize),
            imageSource = chat.imageSource,
            name = chat.title,
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimens.TinySpace),
        ) {
            Text(
                text = chat.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = chat.lastMessage ?: stringResource(R.string.chats_no_messages),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
            )
        }

        if (chat.hasUnreadMessages) {
            val counterText = chat.unreadMessageCount
                .takeIf { it < 10 }
                ?.toString()
                ?: "9+"

            Text(
                modifier = Modifier
                    .clickable {}
                    .size(Dimens.BadgeMediumSize)
                    .background(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = CircleShape,
                    )
                    .wrapContentSize(),
                text = counterText,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontWeight = FontWeight.Bold,
                fontSize = Dimens.BadgeMediumTextSize,
            )
        }

        Box(
            modifier = Modifier,
        ) {
            var expanded by remember { mutableStateOf(false) }
            IconButton(
                enabled = chat.isEnabled,
                onClick = { expanded = true }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.chats_delete_chat)) },
                    onClick = {
                        onDeleteChat()
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun BoxScope.EmptyList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(Dimens.MediumPadding)
            .align(Alignment.Center),
        verticalArrangement = Arrangement.spacedBy(Dimens.MediumSpace),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Default.Mail,
            modifier = Modifier.size(Dimens.MediumImageSize),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline,
        )

        Text(
            text = stringResource(R.string.chats_empty_message),
            textAlign = TextAlign.Center
        )
        Button(
            onClick = {/* TODO */ }
        ) {
            Text(text = stringResource(R.string.chats_create_chat))
        }
    }
}

private fun Modifier.unreadMessagesBackground(
    hasUnreadMessages: Boolean,
): Modifier = composed {
    if (hasUnreadMessages) {
        background(
            color = MaterialTheme.colorScheme.secondaryContainer,
        )
    } else {
        this
    }
}

@ScreenPreview
@Composable
private fun ChatsContentPreview() = PreviewScreenContent {
    Box(Modifier.fillMaxSize()) {
        ChatsContent(PreviewState)
    }
}

private data object PreviewState : ChatsViewModel.State {
    override val chats: ImmutableList<UiChat> = persistentListOf(
        UiChat(
            id = Id(1),
            title = "John",
            lastMessage = "Hello!",
            unreadMessageCount = 20,
            isEnabled = true,
        ),
        UiChat(
            id = Id(2),
            title = "Jane",
            lastMessage = "Lorem Ipsim Test Test!",
            unreadMessageCount = 1,
            isEnabled = false,
        ),
        UiChat(
            id = Id(3),
            title = "Gendalph White",
            lastMessage = null,
            unreadMessageCount = 0,
            isEnabled = true,
        ),
    )
}
