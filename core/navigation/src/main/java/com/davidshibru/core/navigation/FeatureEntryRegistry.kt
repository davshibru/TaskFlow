package com.davidshibru.core.navigation

import javax.inject.Inject
import kotlin.reflect.KClass

class FeatureEntryRegistry @Inject constructor(
    private val entries: Map<Class<out FeatureEntry>, @JvmSuppressWildcards FeatureEntry>
) {
    fun get(kclass: KClass<out FeatureEntry>): FeatureEntry {
        return entries[kclass.java] ?: error("Feature not found")
    }
}