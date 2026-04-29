package com.davidshibru.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureJvmTarget(jvmTarget: JvmTarget = JvmTarget.JVM_17) {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            this.jvmTarget.set(jvmTarget)
        }
    }
}
