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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
}

rootProject.name = "TaskFlow"
include(":app")
include(":core:navigation")
include(":feature:splash:api")
include(":feature:splash:impl")
include(":feature:auth:api")
include(":feature:auth:impl")
