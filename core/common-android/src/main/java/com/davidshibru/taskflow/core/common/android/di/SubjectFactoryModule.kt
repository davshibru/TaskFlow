package com.davidshibru.taskflow.core.common.android.di

import com.davidshibru.taskflow.core.essentials.container.DefaultSubjectFactory
import com.davidshibru.taskflow.core.essentials.container.SubjectFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubjectFactoryModule {

    @Provides
    @Singleton
    fun provideSubjectFactory(): SubjectFactory {
        return DefaultSubjectFactory(
            cacheTimeoutMillis = 5000L,
        )
    }
}
