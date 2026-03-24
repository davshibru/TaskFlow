pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    includeBuild("build-logic")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "TaskFlow"
include(":app")
include(":template:script")

include(":template:android-library")
include(":template:kotlin-library")

include(":core:essentials")
include(":core:common-android")
include(":features:init:domain")
include(":features:init:presentation")
include(":core:theme")
include(":core:navigation")
include(":features:sign-in:domain")
include(":features:sign-in:presentation")
include(":core:navigation-dsl")
include(":core:presentation")
include(":app-demo")

include(":features:init:demo")
include(":features:sign-in:demo")
include(":data")
include(":glue")
include(":core:data")
include(":core:network")