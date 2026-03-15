plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
}
android {
    namespace = "com.davidshibru.taskflow.core.theme"
}
dependencies {
    implementation(projects.core.essentials)

    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
}