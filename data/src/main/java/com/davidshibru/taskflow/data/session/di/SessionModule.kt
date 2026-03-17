package com.davidshibru.taskflow.data.session.di

import com.davidshibru.taskflow.data.SessionProvider
import com.davidshibru.taskflow.data.session.SessionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface SessionModule {

    @Binds
    fun bindSessionProvider(impl: SessionManagerImpl): SessionProvider
}