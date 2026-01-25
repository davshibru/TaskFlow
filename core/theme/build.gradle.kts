plugins {
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.davidshibru.taskflow.core.theme"
}

dependencies {
    implementation(projects.core.essentials)

//    implementation(libs.container)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ===== compose =====
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}