plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.serialization)
}
android {
    namespace = "com.davidshibru.taskflow.feature.signup.presentation"
    resourcePrefix = "sign_up_"
}
dependencies {
    implementation(projects.features.signUp.domain)

    implementation(projects.core.theme)
    implementation(projects.core.essentials)
    implementation(projects.core.presentation)
    implementation(projects.core.navigationDsl)


    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)

    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
