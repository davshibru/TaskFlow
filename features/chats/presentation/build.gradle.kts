plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.serialization)
}
android {
    namespace = "com.davidshibru.taskflow.feature.chats.presentation"
    resourcePrefix = "chats_"
}
dependencies {
    implementation(projects.features.chats.domain)


    implementation(projects.core.theme)
    implementation(projects.core.essentials)
    implementation(projects.core.presentation)
    implementation(projects.core.navigationDsl)
    testImplementation(projects.core.presentationTest)

    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}