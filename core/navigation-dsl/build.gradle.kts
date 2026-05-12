plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
}
android {
    namespace = "com.davidshibru.taskflow.core.navigation.dsl"
}
dependencies {
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.material)

    testImplementation(libs.junit)

    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation)
}