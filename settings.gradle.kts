pluginManagement {
    includeBuild("build-logic")
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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TaskFlow"
include(":app")
include(":navigation")
include(":core:theme")
include(":core:essentials")
include(":core:common-android")
include(":core:navigation-dsl")

include(":features:init:domain")
include(":features:init:presentation")

include(":features:sign-in:domain")
include(":features:sign-in:presentation")

include(":templates:android-library")
include(":templates:kotlin-libraty")
include(":templates:feature:domain")
include(":templates:feature:presentation")
