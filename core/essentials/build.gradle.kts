plugins {
    alias(libs.plugins.convention.kotlin.library)
}
dependencies {
    api(libs.javax.inject)
    api(libs.coroutines.core)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
}
