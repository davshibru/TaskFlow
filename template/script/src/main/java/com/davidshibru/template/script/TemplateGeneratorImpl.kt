package com.davidshibru.template.script

import com.davidshibru.template.script.ProjectUtils.toTypeSafeAccessor
import java.io.File

class TemplateGeneratorImpl {
    fun generate(args: InputArgs) {
        val basePath = args.moduleName.replace(":", "/").removePrefix("/")
        val simpleName = getClassName(args)

        when (args.templateName) {
            "feature" -> generateFeatureModule(basePath, simpleName, args)
            "kotlin-library" -> generateKotlinLibrary(basePath, simpleName, args)
            "android-library" -> generateAndroidLibrary(basePath, simpleName, args)
            else -> println("❌ Unknown template: ${args.templateName}")
        }
    }

    private fun generateFeatureModule(basePath: String, featureName: String, args: InputArgs) {
        println("🚀 Creating feature module: $featureName...")

        // --- 1. Создаем Domain ---
        val domainModuleName = "${args.moduleName}:domain"
        val domainPackageName = "${args.packageName}.domain"
        val domainArgs = args.copy(
            moduleName = domainModuleName,
            packageName = domainPackageName,
            features = args.features.filter { it != "--hilt" && it != "--compose" }
        )

        generateKotlinLibrary(
            path = "$basePath/domain",
            className = "${featureName}UseCase",
            args = domainArgs,
            predefinedDependencies = listOf(
                "implementation(projects.core.essentials)",
            ),
        )

        ProjectUtils.writeSourceFile(
            "$basePath/domain", domainPackageName, "${featureName}UseCase.kt",
            CodeTemplates.useCaseInterface(domainPackageName, featureName)
        )

        // --- 2. Создаем Presentation ---
        val presentationArgs = args.copy(
            moduleName = "${args.moduleName}:presentation",
            packageName = "${args.packageName}.presentation"
        )

        val domainAccessor = toTypeSafeAccessor(domainModuleName)

        generateAndroidLibrary(
            path = "$basePath/presentation",
            className = null,
            args = presentationArgs,
            predefinedDependencies = listOf(
                "implementation($domainAccessor)",
                "implementation(projects.core.essentials)",
                "implementation(projects.core.essentials)",
            ),
            extraDependencies = listOf(
                "androidTestImplementation(libs.androidx.junit)",
                "androidTestImplementation(libs.androidx.espresso.core)"
            )
        )

        ProjectUtils.writeSourceFile(
            "$basePath/presentation", presentationArgs.packageName, "${featureName}Router.kt",
            CodeTemplates.routerInterface(presentationArgs.packageName, featureName)
        )

        ProjectUtils.writeSourceFile(
            "$basePath/presentation", presentationArgs.packageName, "${featureName}ViewModel.kt",
            CodeTemplates.viewModelClass(presentationArgs.packageName, featureName)
        )

        ProjectUtils.writeSourceFile(
            "$basePath/presentation", presentationArgs.packageName, "${featureName}Screen.kt",
            CodeTemplates.screenClass(presentationArgs.packageName, featureName)
        )

        ProjectUtils.addToGitIfRequested("$basePath/presentation/src/main/java", args)
    }

    private fun generateAndroidLibrary(
        path: String,
        className: String? = null,
        args: InputArgs,
        predefinedDependencies: List<String> = emptyList(),
        extraDependencies: List<String> = emptyList()
    ) {
        ProjectUtils.ensureParentBuildFilesExist(path, args)
        val plugins = mutableListOf("alias(libs.plugins.convention.android.library)")

        if (args.features.contains("--compose")) plugins.add("alias(libs.plugins.convention.compose)")
        if (args.features.contains("--hilt")) plugins.add("alias(libs.plugins.convention.hilt)")

        val baseDeps = mutableListOf(
            "implementation(libs.androidx.core.ktx)",
            "implementation(libs.androidx.appcompat)",
            "implementation(libs.material)",
            "testImplementation(libs.junit)"
        )
        if (args.features.contains("--coroutines")) {
            baseDeps.add("implementation(libs.kotlinx.coroutines.android)")
            baseDeps.add("testImplementation(libs.kotlinx.coroutines.test)")
        }

        if (args.features.contains("--navigation")) {
            baseDeps.add("implementation(libs.navigation.compose)")

            if (args.features.contains("--hilt")) {
                baseDeps.add("implementation(libs.hilt.navigation)")
            }
        }

        val allDeps = predefinedDependencies + baseDeps + extraDependencies

        val buildGradle = """
            plugins {
                ${plugins.joinToString("\n                ")}
            }
            android {
                namespace = "${args.packageName}"
            }
            dependencies {
                ${allDeps.joinToString("\n                ")}
            }
        """.trimIndent()

        ProjectUtils.writeFile("$path/build.gradle.kts", buildGradle)

        if (className != null) {
            ProjectUtils.writeSourceFile(
                path,
                args.packageName,
                "$className.kt",
                CodeTemplates.androidClass(args.packageName, className)
            )
        }

        generateStandardFiles(path)

        val defaultClassName =
            className ?: path.substringAfterLast("/").replaceFirstChar { it.uppercase() }
        generateUnitTestFile(path, args.packageName, defaultClassName, args)

        finishModuleGeneration(path, args)
    }

    private fun generateKotlinLibrary(
        path: String,
        className: String,
        args: InputArgs,
        predefinedDependencies: List<String> = emptyList(),
        extraDependencies: List<String> = emptyList(),
    ) {
        ProjectUtils.ensureParentBuildFilesExist(path, args)

        val allDeps = predefinedDependencies + listOf("testImplementation(libs.junit)") + extraDependencies

        val buildGradle = """
            plugins {
                id("java-library")
                alias(libs.plugins.jetbrains.kotlin.jvm)
            }
            java {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            kotlin {
                compilerOptions { jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17 }
            }
            dependencies {
                ${allDeps.joinToString("\n                ")}
            }
        """.trimIndent()

        ProjectUtils.writeFile("$path/build.gradle.kts", buildGradle)
        ProjectUtils.writeSourceFile(
            path,
            args.packageName,
            "$className.kt",
            CodeTemplates.kotlinClass(args.packageName, className)
        )

        ProjectUtils.writeFile("$path/.gitignore", "/build")
        generateUnitTestFile(path, args.packageName, className, args)

        finishModuleGeneration(path, args)
    }

    // --- Общие хелперы для генератора ---

    private fun generateStandardFiles(path: String) {
        ProjectUtils.writeFile("$path/.gitignore", "/build")
        ProjectUtils.writeFile("$path/proguard-rules.pro", CodeTemplates.proguardRules())
        ProjectUtils.writeFile("$path/consumer-rules.pro", "# Rules for library consumers.")
        ProjectUtils.writeFile(
            "$path/src/main/AndroidManifest.xml",
            """<?xml version="1.0" encoding="utf-8"?><manifest />"""
        )
    }

    private fun generateUnitTestFile(
        path: String,
        packageName: String,
        className: String,
        args: InputArgs
    ) {
        val testDirPath = "$path/src/test/java/${packageName.replace('.', '/')}"
        File(testDirPath).mkdirs()
        val fileName = "${className}Test.kt"

        ProjectUtils.writeFile(
            "$testDirPath/$fileName",
            CodeTemplates.unitTest(packageName, className)
        )
        println("   🧪 Created Test file: $fileName")
        ProjectUtils.addToGitIfRequested(testDirPath, args)
    }

    private fun finishModuleGeneration(path: String, args: InputArgs) {
        ProjectUtils.appendToSettings(args.moduleName)
        ProjectUtils.addToGitIfRequested(path, args)
        println("✅ Finished generating module at: $path")
    }

    private fun getClassName(args: InputArgs): String {
        return args.moduleName.substringAfterLast(":").split("-", "_").joinToString("") {
            it.replaceFirstChar { char -> char.uppercase() }
        }
    }
}