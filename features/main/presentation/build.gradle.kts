plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.serialization)
}
android {
    namespace = "com.davidshibru.taskflow.features.main.presentation"
    resourcePrefix = "main_"
}
dependencies {
    implementation(projects.features.main.domain)
    implementation(projects.core.essentials)
    implementation(projects.core.theme)
    implementation(projects.core.presentation)
    implementation(projects.core.navigationDsl)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation)
    testImplementation(projects.core.presentationTest)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}