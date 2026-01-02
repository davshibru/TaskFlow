package com.davidshibru.feature.splash.impl.di

import com.davidshibru.feature.splash.impl.SplashEntryImpl
import com.davidshibru.taskflow.core.navigation.FeatureEntry
import com.davidshibru.taskflow.core.navigation.di.FeatureEntryKey
import com.davidshibru.taskflow.feature.splash.api.SplashEntry
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class SplashEntryModule {

    @Binds
    @IntoMap
    @FeatureEntryKey(SplashEntry::class)
    abstract fun bindSplashEntry(impl: SplashEntryImpl) : FeatureEntry
}