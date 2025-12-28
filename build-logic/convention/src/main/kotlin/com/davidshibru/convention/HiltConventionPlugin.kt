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

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("*** HiltConventionPlugin invoked ***")

        with(target) {
            pluginManager.withPlugin("com.android.application") {
                extensions.configure<ApplicationExtension> {
                    configureHiltConvention(this)
                }
            }

            pluginManager.withPlugin("com.android.library") {
                extensions.configure<LibraryExtension> {
                    configureHiltConvention(this)
                }
            }

        }
    }
}

private fun Project.configureHiltConvention(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        pluginManager.apply("com.google.devtools.ksp")
        pluginManager.apply("com.google.dagger.hilt.android")

        dependencies {
            val hilt = libs.findLibrary("hilt-core").get()
            add("implementation", hilt)
            val hiltCompiler = libs.findLibrary("hilt-compiler").get()
            add("ksp", hiltCompiler)
        }
    }
}
