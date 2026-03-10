package com.davidshibru.taskflow.core.essentials.resources

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringProviderStore @Inject constructor(
    @PublishedApi
    internal val stringsProviders: Map<Class<*>, @JvmSuppressWildcards StringProvider>
) {

    inline operator fun <reified T : StringProvider> invoke(): T {
        return stringsProviders[T::class.java] as T
    }

}