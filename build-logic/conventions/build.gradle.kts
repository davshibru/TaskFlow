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