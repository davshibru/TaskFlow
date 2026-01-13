package com.davidshibru.splash.impl.di

import com.davidshibru.core.navigation.FeatureEntry
import com.davidshibru.core.navigation.di.FeatureEntryKey
import com.davidshibru.splash.api.SplashEntry
import com.davidshibru.splash.impl.SplashEntryImpl
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
    abstract fun bindSplashScreen(impl: SplashEntryImpl) : FeatureEntry

}