package com.davidshibru.taskflow.features.main.domain.di

import com.davidshibru.taskflow.features.main.domain.GetMainUseCase
import com.davidshibru.taskflow.features.main.domain.SaveMainUseCase
import com.davidshibru.taskflow.features.main.domain.usecases.GetMainUseCaseImpl
import com.davidshibru.taskflow.features.main.domain.usecases.SaveMainUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface UseCasesModule {

    @Binds
    fun bindGetMainUseCase(impl: GetMainUseCaseImpl): GetMainUseCase

    @Binds
    fun bindSaveMainUseCase(impl: SaveMainUseCaseImpl): SaveMainUseCase
}