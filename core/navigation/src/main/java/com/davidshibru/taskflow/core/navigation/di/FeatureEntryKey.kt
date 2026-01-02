package com.davidshibru.taskflow.core.navigation.di

import com.davidshibru.taskflow.core.navigation.FeatureEntry
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class FeatureEntryKey(val value: KClass<out FeatureEntry>)
