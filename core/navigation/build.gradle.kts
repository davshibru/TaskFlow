plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.serialization)
}
android {
    namespace = "com.davidshibru.taskflow.core.navigation"
}
dependencies {
    implementation(projects.features.init.presentation)
    implementation(projects.features.signIn.presentation)
    implementation(projects.features.signUp.presentation)

    implementation(projects.core.navigationDsl)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)

    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    implementation(libs.androidx.compose.material.icons.extended)
}