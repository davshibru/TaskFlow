package com.davidshibru.template.script

object CodeTemplates {

    fun parentBuildGradle() = """
        tasks.register<Delete>("clean") {
            delete(layout.buildDirectory)
        }
    """.trimIndent()

    fun unitTest(packageName: String, className: String) = """
        package $packageName

        import org.junit.Assert.assertEquals
        import org.junit.Test

        class ${className}Test {
            @Test
            fun `initial test`() {
                assertEquals("Test should pass", 4, 2 + 2)
            }
        }
    """.trimIndent()

    fun useCaseInterface(packageName: String, featureName: String) = """
        package $packageName
        
        interface ${featureName}UseCase {
            operator fun invoke()
        }
    """.trimIndent()

    fun routerInterface(packageName: String, featureName: String) = """
        package $packageName
        
        interface ${featureName}Router {
            fun navigateBack()
        }
    """.trimIndent()

    fun androidClass(packageName: String, className: String) = """
        package $packageName
        
        import android.content.Context
        import android.widget.Toast
        
        class $className {
            fun hello(context: Context) {
                Toast.makeText(context, "Hello from ${className}!", Toast.LENGTH_SHORT).show()
            }
        }
    """.trimIndent()

    fun kotlinClass(packageName: String, className: String) = """
        package $packageName
        
        class $className {
            // TODO: Implement logic for $className
        }
    """.trimIndent()

    fun proguardRules() = """
        # Add project specific ProGuard rules here.
        # By default, the flags in this file are appended to flags specified
        # in ${'$'}ANDROID_HOME/tools/proguard/proguard-android.txt
    """.trimIndent()
}