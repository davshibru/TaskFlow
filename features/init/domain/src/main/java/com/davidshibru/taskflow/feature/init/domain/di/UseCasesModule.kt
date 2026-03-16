package com.davidshibru.taskflow.feature.init.domain.di

import com.davidshibru.taskflow.feature.init.domain.IsAuthorizedUseCase
import com.davidshibru.taskflow.feature.init.domain.ShowRandomKeyFeatureUseCase
import com.davidshibru.taskflow.feature.init.domain.usecases.IsAuthorizedUseCaseImpl
import com.davidshibru.taskflow.feature.init.domain.usecases.ShowRandomKeyFeatureUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal interface UseCasesModule {

    @Binds
    fun bindUsAuthorizedUseCase(
        impl: IsAuthorizedUseCaseImpl
    ) : IsAuthorizedUseCase

    @Binds
    fun bindShowRandomKeyFeatureUseCase(
        impl: ShowRandomKeyFeatureUseCaseImpl
    ) : ShowRandomKeyFeatureUseCase
}