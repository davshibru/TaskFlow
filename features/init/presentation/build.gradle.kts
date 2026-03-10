plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
}
android {
    namespace = "com.davidshibru.taskflow.feature.init.presentation"
}
dependencies {
    implementation(project(":features:init:domain"))
    implementation(projects.core.essentials)

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
}