package com.davidshibru.auth.impl.di

import com.davidshibru.auth.api.AuthEntry
import com.davidshibru.auth.impl.AuthEntryImpl
import com.davidshibru.core.navigation.FeatureEntry
import com.davidshibru.core.navigation.di.FeatureEntryKey
import dagger.Binds
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
    abstract fun authEntryBinds(impl: AuthEntryImpl) : FeatureEntry

}