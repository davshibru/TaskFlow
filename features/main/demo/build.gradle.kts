plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
}
android {
    namespace = "com.davidshibru.taskflow.features.main.demo"
    
}
dependencies {
    implementation(projects.features.main.domain)
    implementation(projects.core.essentials)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
}