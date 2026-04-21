package com.davidshibru.taskflow.core.common.android

import timber.log.Timber
import kotlin.reflect.KClass

internal fun Timber.DebugTree.addIgnoredClass(
    vararg classes: KClass<*>
) = apply {
    val fqcnIgnoreField = this::class.java.getDeclaredField("fqcnIgnore")
    fqcnIgnoreField.isAccessible = true
    val originList = fqcnIgnoreField.get(this) as List<String>
    val updatedList = originList + classes.map { klass -> klass.java.name }
    fqcnIgnoreField.set(this, updatedList)
}