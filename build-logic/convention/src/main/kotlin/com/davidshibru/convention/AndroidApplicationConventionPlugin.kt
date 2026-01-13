package com.davidshibru.convention

import com.android.build.api.dsl.ApplicationExtension
import com.davidshibru.convention.utils.Utils.toInt
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        println("*** AndroidApplicationConventionPlugin invoke ***")

        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
        }

        extensions.configure<ApplicationExtension> {
            configureAndroidApplication(this)
        }
    }
}

private fun Project.configureAndroidApplication(androidExtension: ApplicationExtension) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    androidExtension.apply {
        val androidCompileSdk = libs.findVersion("androidCompileSdk").get().toInt()
        compileSdk = androidCompileSdk

        defaultConfig {
            val androidMinSdk = libs.findVersion("androidMinSdk").get().toInt()
            val androidTargetSdk = libs.findVersion("androidTargetSdk").get().toInt()

            minSdk = androidMinSdk
            targetSdk = androidTargetSdk

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
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
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}