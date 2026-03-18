package com.davidshibru.taskflow.data.accounts.di

import com.davidshibru.taskflow.data.AccountsDataRepository
import com.davidshibru.taskflow.data.accounts.AccountsDataRepositoryImpl
import com.davidshibru.taskflow.data.accounts.remote.AccountsApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal interface AccountsModule {

    @Binds
    fun bindAccountsDataRepository(
        impl: AccountsDataRepositoryImpl,
    ): AccountsDataRepository

    @Provides
    fun provideAccountsApi(retrofit: Retrofit): AccountsApi {
        return retrofit.create(AccountsApi::class.java)
    }
}