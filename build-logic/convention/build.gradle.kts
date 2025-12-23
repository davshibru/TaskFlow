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
            id = "com.davidshibru.convention.application"
            implementationClass = "com.davidshibru.convention.AndroidApplicationConventionPlugin"
        }
        create("customCompose") {
            id = "com.davidshibru.convention.compose"
            implementationClass = "com.davidshibru.convention.ComposeConventionPlugin"
        }
    }
}