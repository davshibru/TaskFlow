package com.davidshibru.convention

import com.android.build.api.dsl.LibraryExtension
import com.davidshibru.convention.utils.Utils.toInt
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target){
        println("*** AndroidLibraryConventionPlugin invoke ***")

        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        extensions.configure<LibraryExtension>() {
            configureAndroidLibrary(this)
        }
    }
}

private fun Project.configureAndroidLibrary(libraryExtension: LibraryExtension) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    libraryExtension.apply {
        val androidCompileSdk = libs.findVersion("androidCompileSdk").get().toInt()

        compileSdk {
            version = release(androidCompileSdk)
        }

        val androidMinSdk = libs.findVersion("androidMinSdk").get().toInt()

        defaultConfig {
            minSdk = androidMinSdk
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
}