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
    override fun apply(target: Project) {
        println("*** ComposeConventionPlugin invoked ***")

        with(target) {
            pluginManager.withPlugin("com.android.application") {
                extensions.configure<ApplicationExtension> {
                    configureCompose(this)
                }
            }

            pluginManager.withPlugin("com.android.library") {
                extensions.configure<LibraryExtension>() {
                    configureCompose(this)
                }
            }
        }
    }
}

private fun Project.configureCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("kotlinCompilerExtensionVersion").get().toString()
        }
    }
}