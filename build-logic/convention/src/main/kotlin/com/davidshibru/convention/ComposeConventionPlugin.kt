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

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        println("*** ComposeConventionPlugin invoke***")

        with(pluginManager) {
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        pluginManager.withPlugin("com.android.application") {
            extensions.configure<ApplicationExtension> {
                configureComposePlugin(this)
            }
        }
        pluginManager.withPlugin("com.android.library") {
            extensions.configure<LibraryExtension> {
                configureComposePlugin(this)
            }
        }
    }
}

private fun Project.configureComposePlugin(commonExtension: CommonExtension) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    commonExtension.apply {
        dependencies {
            val composeBom = libs.findLibrary("androidx.compose.bom").get()
            add("implementation", project.dependencies.platform(composeBom))
            add("implementation", libs.findLibrary("androidx.ui").get())
            add("implementation", libs.findLibrary("androidx.ui.graphics").get())
            add("implementation", libs.findLibrary("androidx.ui.tooling.preview").get())
            add("implementation", libs.findLibrary("androidx.material3").get())
        }
    }
}
