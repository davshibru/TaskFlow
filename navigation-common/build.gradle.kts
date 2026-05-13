plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.davidshibru.taskflow.navigation.common"
}

dependencies {
    implementation(projects.core.navigationDsl)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.hilt.navigation)
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.kotlinx.collections.immutable)
}
