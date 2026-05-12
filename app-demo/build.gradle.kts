plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.serialization)
}

android {
    namespace = "com.davidshibru.taskflow.demo"

    flavorDimensions += "feature"

    productFlavors {
        create("init") {
            dimension = "feature"
            applicationIdSuffix = ".init"
        }
        create("chats") {
            dimension = "feature"
            applicationIdSuffix = ".chats"
        }
        create("signin") {
            dimension = "feature"
            applicationIdSuffix = ".signin"
        }
        create("signup") {
            dimension = "feature"
            applicationIdSuffix = ".signup"
        }

        create("mainFeature") {
            dimension = "feature"
            applicationIdSuffix = ".main"
        }
    }

    defaultConfig {
        applicationId = "com.davidshibru.taskflow.demo"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(projects.core.essentials)
    implementation(projects.core.commonAndroid)
    implementation(projects.core.navigationDsl)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.hilt.navigation)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.kotlinx.collections.immutable)

    "initImplementation"(projects.features.init.presentation)
    "initImplementation"(projects.features.init.demo)
    "chatsImplementation"(projects.features.chats.presentation)
    "chatsImplementation"(projects.features.chats.demo)
    "signinImplementation"(projects.features.signIn.presentation)
    "signinImplementation"(projects.features.signIn.demo)
    "signupImplementation"(projects.features.signUp.presentation)
    "signupImplementation"(projects.features.signUp.demo)

    "mainFeatureImplementation"(projects.features.main.presentation)
    "mainFeatureImplementation"(projects.features.main.demo)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
