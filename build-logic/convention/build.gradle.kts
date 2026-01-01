plugins {
    `kotlin-dsl`
}

group = "com.davidshibru.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        create("application-convention") {
            id = "com.davidshibru.convention.application.convention"
            implementationClass = "com.davidshibru.convention.AndroidApplicationConventionPlugin"
        }
        create("compose-convention") {
            id = "com.davidshibru.convention.compose.convention"
            implementationClass = "com.davidshibru.convention.ComposeConventionPlugin"
        }
        create("hilt-convention") {
            id = "com.davidshibru.convention.hilt.convention"
            implementationClass = "com.davidshibru.convention.HiltConventionPlugin"
        }
        create("andrpod-library-convention") {
            id = "com.davidshibru.convention.android.library.convention"
            implementationClass = "com.davidshibru.convention.AndroidLibraryConventionPlugin"
        }
    }
}