package com.davidshibru.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        println("*** ComposeConventionPlugin invoke ***")

        pluginManager.withPlugin("com.android.application") {
            extensions.configure<ApplicationExtension>() {
                configureComposeConvention(this)
            }
        }

        pluginManager.withPlugin("com.android.library") {
            extensions.configure<LibraryExtension>() {
                configureComposeConvention(this)
            }
        }
    }
}

private fun Project.configureComposeConvention(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures.compose = true

        val kotlinCompilerExtensionVersion =
            libs.findVersion("kotlinCompilerExtensionVersion").get().toString()

        composeOptions.kotlinCompilerExtensionVersion = kotlinCompilerExtensionVersion

    }
}