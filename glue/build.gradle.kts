plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.hilt)
}
android {
    namespace = "com.davidshibru.taskflow.glue"
}
dependencies {
    implementation(projects.features.init.domain)
    implementation(projects.features.signIn.domain)
    implementation(projects.features.signUp.domain)
    implementation(projects.features.chats.domain)

    implementation(projects.data)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
}