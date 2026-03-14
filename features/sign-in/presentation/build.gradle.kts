plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.serialization)
}
android {
    namespace = "com.davidshibru.taskflow.features.signin.presentation"
}
dependencies {
    implementation(projects.features.signIn.domain)

    implementation(projects.core.theme)
    implementation(projects.core.essentials)
    implementation(projects.core.navigationDsl)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)

    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}