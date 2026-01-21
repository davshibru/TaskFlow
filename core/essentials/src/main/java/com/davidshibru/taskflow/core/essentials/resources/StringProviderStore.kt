package com.davidshibru.taskflow.core.essentials.resources

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringProviderStore @Inject constructor(
    @PublishedApi
    internal val stringProviders: Map<Class<*>, @JvmSuppressWildcards StringProvider>,
) {

    inline operator fun <reified T: StringProvider> invoke(): T {
        return stringProviders[T::class.java] as T
    }
}