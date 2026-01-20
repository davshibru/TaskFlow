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

        with(pluginManager) {
            apply("com.google.devtools.ksp")
            apply("com.google.dagger.hilt.android")

            withPlugin("com.android.application") {
                extensions.configure<ApplicationExtension> {
                    configureHiltConvention(this)
                }
            }

            withPlugin("com.android.library") {
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
        val hiltCore = libs.findLibrary("hilt-core").get()
        val hiltCompiler = libs.findLibrary("hilt-compiler").get()

        dependencies {
            add("implementation", hiltCore)
            add("ksp", hiltCompiler)
        }
    }
}