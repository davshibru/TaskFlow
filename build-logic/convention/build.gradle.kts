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
        create("androidApplication") {
            id = "com.davidshibru.convention.android.application"
            implementationClass = "com.davidshibru.convention.AndroidApplicationConventionPlugin"
        }
        create("androidLibrary") {
            id = "com.davidshibru.convention.android.library"
            implementationClass = "com.davidshibru.convention.AndroidLibraryConventionPlugin"
        }
        create("compose") {
            id = "com.davidshibru.convention.compose"
            implementationClass = "com.davidshibru.convention.ComposeConventionPlugin"
        }
        create("hilt") {
            id = "com.davidshibru.convention.hilt"
            implementationClass = "com.davidshibru.convention.HiltConventionPlugin"
        }
    }
}
