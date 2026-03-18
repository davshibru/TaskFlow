package com.davidshibru.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class RetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        println("*** RetrofitConventionPlugin invoke ***")

        with(pluginManager) {
            apply("com.davidshibru.convention.serialization")
        }

        pluginManager.withPlugin("com.android.application") {
            extensions.configure<ApplicationExtension> {
                configureRetrofitPlugin(this)
            }
        }
        pluginManager.withPlugin("com.android.library") {
            extensions.configure<LibraryExtension> {
                configureRetrofitPlugin(this)
            }
        }
    }
}

fun Project.configureRetrofitPlugin(commonExtension: CommonExtension) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        dependencies {
            add("implementation", libs.findLibrary("retrofit").get())
            add("implementation", libs.findLibrary("retrofit-converter-kotlinx-serialization").get())
            add("implementation", libs.findLibrary("okhttp").get())
            add("implementation", libs.findLibrary("okhttp-logging-interceptor").get())
        }
    }
}
