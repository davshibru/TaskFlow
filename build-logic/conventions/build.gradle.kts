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
        create("android-application") {
            id = "com.davidshibru.conventions.android.application"
            implementationClass = "com.davidshibru.conventions.AndroidApplicationConventionPlugin"
        }
        create("android-library") {
            id = "com.davidshibru.conventions.android.library"
            implementationClass = "com.davidshibru.conventions.AndroidLibraryConventionPlugin"
        }
        create("compose") {
            id = "com.davidshibru.conventions.compose"
            implementationClass = "com.davidshibru.conventions.ComposeConventionPlugin"
        }
        create("hilt") {
            id = "com.davidshibru.conventions.hilt"
            implementationClass = "com.davidshibru.conventions.HiltConventionPlugin"
        }
    }
}