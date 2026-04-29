plugins {
    alias(libs.plugins.convention.kotlin.library)
    alias(libs.plugins.convention.retrofit)
}

dependencies {
    implementation(projects.core.essentials)
    testImplementation(libs.junit)

}
