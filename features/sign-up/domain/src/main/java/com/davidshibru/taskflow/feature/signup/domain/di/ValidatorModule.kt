package com.davidshibru.taskflow.feature.signup.domain.di

import com.davidshibru.taskflow.feature.signup.domain.validators.NewAccountValidator
import com.davidshibru.taskflow.feature.signup.domain.validators.NewAccountValidatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ValidatorModule {

    @Binds
    fun bindNewAccountValidation(impl: NewAccountValidatorImpl): NewAccountValidator

}