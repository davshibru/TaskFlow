plugins {
    alias(libs.plugins.convention.kotlin.library)
    alias(libs.plugins.ksp)
}
dependencies {
    implementation(projects.core.essentials)

    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)

    implementation(libs.javax.inject)
    testImplementation(libs.junit)
}
