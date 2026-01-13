package com.davidshibru.convention.utils

import org.gradle.api.artifacts.VersionConstraint
import kotlin.text.toInt

internal object Utils {

    fun VersionConstraint.toInt() : Int {
        return toString().toInt()
    }

}