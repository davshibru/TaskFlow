package com.davidshibru.taskflow.features.profile.domain.di

import com.davidshibru.taskflow.features.profile.domain.GetProfileUseCase
import com.davidshibru.taskflow.features.profile.domain.SaveProfileUseCase
import com.davidshibru.taskflow.features.profile.domain.usecases.GetProfileUseCaseImpl
import com.davidshibru.taskflow.features.profile.domain.usecases.SaveProfileUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface UseCasesModule {

    @Binds
    fun bindGetProfileUseCase(impl: GetProfileUseCaseImpl): GetProfileUseCase

    @Binds
    fun bindSaveProfileUseCase(impl: SaveProfileUseCaseImpl): SaveProfileUseCase
}