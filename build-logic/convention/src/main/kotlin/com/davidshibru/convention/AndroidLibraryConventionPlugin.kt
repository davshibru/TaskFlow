package com.davidshibru.convention

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("*** AndroidLibraryConventionPlugin invoked ***")

        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")

            extensions.configure<LibraryExtension>() {
                configureAndroidLibrary(this)
            }
        }
    }
}

private fun Project.configureAndroidLibrary(commonExtension: LibraryExtension) {
    commonExtension.apply {
        compileSdk = 36

        defaultConfig {
            minSdk = 26

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs += listOf(
                "-Xjvm-default=all"
            )
        }
    }
}