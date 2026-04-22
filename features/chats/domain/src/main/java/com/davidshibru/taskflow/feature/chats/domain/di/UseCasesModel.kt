package com.davidshibru.taskflow.feature.chats.domain.di

import com.davidshibru.taskflow.feature.chats.domain.DeleteChatUseCase
import com.davidshibru.taskflow.feature.chats.domain.GetChatsUseCase
import com.davidshibru.taskflow.feature.chats.domain.usecases.DeleteChatUseCaseImpl
import com.davidshibru.taskflow.feature.chats.domain.usecases.GetChatsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface UseCasesModel {

    @Binds
    fun bindGetChatsUseCase(impl: GetChatsUseCaseImpl): GetChatsUseCase

    @Binds
    fun bindDeleteChatUseCase(impl: DeleteChatUseCaseImpl): DeleteChatUseCase
}