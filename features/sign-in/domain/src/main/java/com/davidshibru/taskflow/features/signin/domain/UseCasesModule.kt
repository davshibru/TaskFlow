package com.davidshibru.taskflow.features.signin.domain

import com.davidshibru.taskflow.features.signin.domain.usecases.SignInUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface UseCasesModule {

    @Binds
    fun bindSignInUseCase(impl: SignInUseCaseImpl): SignInUseCase

}