plugins {
    alias(libs.plugins.convention.kotlin.library)
}

dependencies {
    implementation(libs.javax.inject)
    api(libs.coroutines.core)
}