plugins {
    alias(libs.plugins.convention.kotlin.library)
}

dependencies {
    implementation(libs.javax.inject)

//    implementation(libs.container)
    implementation(projects.core.essentials)
}
