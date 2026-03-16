plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.serialization)
}
android {
    namespace = "com.davidshibru.taskflow.feature.init.presentation"
}
dependencies {
    implementation(projects.features.init.domain)

    implementation(projects.core.theme)
    implementation(projects.core.essentials)
    implementation(projects.core.presentation)
    implementation(projects.core.navigationDsl)

    // ===== base =====
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ===== navigation =====
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation)


//    implementation(libs.container)
}