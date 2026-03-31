plugins {
    alias(libs.plugins.convention.android.library)
}
android {
    namespace = "com.davidshibru.taskflow.core.presentation.test"
}
dependencies {
    api(projects.core.presentation)
    api(projects.core.essentials)

    api(libs.androidx.appcompat)
    api(libs.junit)
    api(libs.mockk)
    api(libs.coroutines.test)
    api(libs.turbine)
}