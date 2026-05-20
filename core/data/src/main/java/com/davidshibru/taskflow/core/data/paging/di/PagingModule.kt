package com.davidshibru.taskflow.core.data.paging.di

import com.davidshibru.taskflow.core.data.paging.PagingUtils
import com.davidshibru.taskflow.core.data.paging.PagingUtilsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface PagingModule {

    @Binds
    fun bindPagingUnit(impl: PagingUtilsImpl): PagingUtils
}