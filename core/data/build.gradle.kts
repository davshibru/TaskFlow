plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.retrofit)
    alias(libs.plugins.convention.hilt)
}
android {
    namespace = "com.davidshibru.taskflow.core.data"
}
dependencies {
    implementation(projects.core.essentials)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
}