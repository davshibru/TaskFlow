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
            id = "com.davidshibru.convention.application.android"
            implementationClass = "com.davidshibru.convention.ApplicationAndroidConventionPlugin"
        }
        create("compose-convention") {
            id = "com.davidshibru.convention.compose"
            implementationClass = "com.davidshibru.convention.ComposeConventionPlugin"
        }
        create("hilt-convention") {
            id = "com.davidshibru.convention.hilt"
            implementationClass = "com.davidshibru.convention.HiltConventionPlugin"
        }
    }
}

