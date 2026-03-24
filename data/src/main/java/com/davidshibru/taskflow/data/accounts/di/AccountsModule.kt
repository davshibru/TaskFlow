package com.davidshibru.taskflow.data.accounts.di

import com.davidshibru.taskflow.data.AccountsDataRepository
import com.davidshibru.taskflow.data.accounts.AccountsDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AccountsModule {

    @Binds
    fun bindAccountsDataRepository(
        impl: AccountsDataRepositoryImpl,
    ): AccountsDataRepository

}