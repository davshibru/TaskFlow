package com.davidshibru.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class RetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        println("*** RetrofitConventionPlugin invoke ***")

        with(pluginManager) {
            apply("com.davidshibru.convention.serialization")
        }

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        dependencies {
            add("implementation", libs.findLibrary("retrofit").get())
            add("implementation", libs.findLibrary("retrofit-converter-kotlinx-serialization").get())
            add("implementation", libs.findLibrary("okhttp").get())
            add("implementation", libs.findLibrary("okhttp-logging-interceptor").get())
        }
    }
}
