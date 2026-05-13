plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.serialization)
}

android {
    namespace = "com.davidshibru.taskflow"

    defaultConfig {
        applicationId = "com.davidshibru.taskflow"
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.theme)
    implementation(projects.core.commonAndroid)
    implementation(projects.navigation)

    implementation(projects.glue)

    implementation(projects.features.init.presentation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
}
