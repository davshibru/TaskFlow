package com.davidshibru.taskflow.feature.signup.domain.di

import com.davidshibru.taskflow.feature.signup.domain.SignUpUseCase
import com.davidshibru.taskflow.feature.signup.domain.ValidateAccountUseCase
import com.davidshibru.taskflow.feature.signup.domain.usecases.SignUpUseCaseImpl
import com.davidshibru.taskflow.feature.signup.domain.usecases.ValidateAccountUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface UseCaseModule {

    @Binds
    fun bindSignUpUseCase(impl: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    fun bindValidateAccountUseCase(impl: ValidateAccountUseCaseImpl): ValidateAccountUseCase

}