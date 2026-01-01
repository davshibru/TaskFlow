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
    override fun apply(target: Project) = with(target) {
        println("*** HiltConventionPlugin invoke ***")

        pluginManager.withPlugin("com.android.application") {
            extensions.configure<ApplicationExtension> {
                hiltConvention(this)
            }
        }

        pluginManager.withPlugin("com.android.library") {
            extensions.configure<LibraryExtension> {
                hiltConvention(this)
            }
        }
    }
}

private fun Project.hiltConvention(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        pluginManager.apply("com.google.devtools.ksp")
        pluginManager.apply("com.google.dagger.hilt.android")

        dependencies {
            val hiltCore = libs.findLibrary("hilt-core").get()
            val hiltCompiler = libs.findLibrary("hilt-compiler").get()

            add("implementation", hiltCore)
            add("ksp", hiltCompiler)
        }
    }
}