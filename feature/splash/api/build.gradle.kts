plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.davidshibru.taskflow.feature.splash.api"
}

dependencies {
    implementation(projects.core.navigation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}