package com.davidshibru.feature.auth.impl.di

import com.davidshibru.feature.auth.api.AuthEntry
import com.davidshibru.feature.auth.impl.AuthEntryImpl
import com.davidshibru.taskflow.core.navigation.FeatureEntry
import com.davidshibru.taskflow.core.navigation.di.FeatureEntryKey
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthEntryModule {

    @Binds
    @IntoMap
    @FeatureEntryKey(AuthEntry::class)
    abstract fun bindAuthEntry(impl: AuthEntryImpl) : FeatureEntry
}