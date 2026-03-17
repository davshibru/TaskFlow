package com.davidshibru.taskflow.data.versioning.di

import com.davidshibru.taskflow.data.VersioningRepository
import com.davidshibru.taskflow.data.versioning.VersioningRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface VersioningModule {
    @Binds
    fun bindVersioningRepository(impl: VersioningRepositoryImpl) : VersioningRepository
}