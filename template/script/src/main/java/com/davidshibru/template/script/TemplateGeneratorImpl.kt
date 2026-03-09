package com.davidshibru.template.script

import java.io.File

class TemplateGeneratorImpl {
    fun generate(args: InputArgs) {
        val basePath = args.moduleName.replace(":", "/").removePrefix("/")
        // Извлекаем имя для класса (например, из ":core:network" -> "Network")
        val simpleName = args.moduleName.substringAfterLast(":").replaceFirstChar { it.uppercase() }

        when (args.templateName) {
            "feature" -> generateFeatureModule(basePath, simpleName, args)
            // Теперь передаем simpleName в обычную библиотеку
            "kotlin-library" -> generateKotlinLibrary(basePath, simpleName, args)
            "android-library" -> generateAndroidLibrary(basePath, args)
            else -> println("❌ Unknown template: ${args.templateName}")
        }
    }

    private fun generateFeatureModule(basePath: String, featureName: String, args: InputArgs) {println("🚀 Creating feature module: $featureName...")

        val domainModuleName = "${args.moduleName}:domain"
        val domainPackageName = "${args.packageName}.domain"

        // --- 1. Создаем Domain ---
        val domainArgs = args.copy(
            moduleName = domainModuleName,
            packageName = domainPackageName,
            features = args.features.filter { it != "--hilt" && it != "--compose" }
        )

        generateKotlinLibrary("$basePath/domain", "${featureName}UseCase", domainArgs)

        val useCaseContent = """
            package $domainPackageName
            
            interface ${featureName}UseCase {
                operator fun invoke()
            }
        """.trimIndent()
        writeSourceFile(
            "$basePath/domain",
            domainPackageName,
            "${featureName}UseCase.kt",
            useCaseContent
        )

        val presentationModuleName = "${args.moduleName}:presentation"
        val presentationPackageName = "${args.packageName}.presentation"

        val presentationArgs = args.copy(
            moduleName = presentationModuleName,
            packageName = presentationPackageName
        )
        // Указываем зависимость от domain-модуля
        val projectDependencies = listOf("implementation(project(\"$domainModuleName\"))")
        generateAndroidLibrary("$basePath/presentation", presentationArgs, projectDependencies)

        // Генерируем Router интерфейс
        val routerContent = """
            package $presentationPackageName
            
            interface ${featureName}Router {
                fun navigateBack()
            }
        """.trimIndent()
        writeSourceFile(
            "$basePath/presentation",
            presentationPackageName,
            "${featureName}Router.kt",
            routerContent
        )
    }

    private fun generateAndroidLibrary(
        path: String,
        args: InputArgs,
        extraDependencies: List<String> = emptyList()
    ) {
        val plugins = mutableListOf("alias(libs.plugins.convention.android.library)")

        // Добавляем дополнительные плагины по флагам
        if (args.features.contains("--compose")) plugins.add("alias(libs.plugins.convention.compose)")
        if (args.features.contains("--hilt")) plugins.add("alias(libs.plugins.convention.hilt)")

        // Базовые зависимости для Android Library
        val baseDependencies = mutableListOf(
            "implementation(libs.androidx.core.ktx)",
            "implementation(libs.androidx.appcompat)",
            "implementation(libs.material)",
            "testImplementation(libs.junit)",
            "androidTestImplementation(libs.androidx.junit)",
            "androidTestImplementation(libs.androidx.espresso.core)",

            "testImplementation(libs.junit)",
            "androidTestImplementation(libs.androidx.junit)",
            "androidTestImplementation(libs.androidx.espresso.core)"
        )

        if (args.features.contains("--coroutines")) {
            baseDependencies.add("implementation(libs.kotlinx.coroutines.android)")
            baseDependencies.add("testImplementation(libs.kotlinx.coroutines.test)")
        }
        // Сливаем базовые и переданные (например, зависимость от domain)
        val allDependencies = baseDependencies + extraDependencies

        val buildGradleContent = """
        plugins {
            ${plugins.joinToString("\n            ")}
        }
        
        android {
            namespace = "${args.packageName}"
        }
        
        dependencies {
            ${allDependencies.joinToString("\n            ")}
        }
    """.trimIndent()

        writeFile("$path/build.gradle.kts", buildGradleContent)

        generateGitIgnore(path)
        generateProguardRules(path)
        generateConsumerRules(path)

        val manifestContent = """<?xml version="1.0" encoding="utf-8"?><manifest />"""
        writeFile("$path/src/main/AndroidManifest.xml", manifestContent)

        appendToSettings(args.moduleName)
        println("✅ Created Android module at: $path")

        addToGitIfRequested(path, args)
    }

    private fun generateKotlinLibrary(
        path: String,
        className: String,
        args: InputArgs,
        extraDependencies: List<String> = emptyList()
    ) {
        val baseDeps = mutableListOf(
            "testImplementation(libs.junit)"
        )
        val allDeps = baseDeps + extraDependencies

        val depsContent = allDeps.joinToString("\n    ")

        val buildGradleContent = """
            plugins {
                id("java-library")
                alias(libs.plugins.jetbrains.kotlin.jvm)
            }
            
            java {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
            
            kotlin {
                compilerOptions {
                    jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
                }
            }
            dependencies {
                $depsContent
            }
        """.trimIndent()

        writeFile("$path/build.gradle.kts", buildGradleContent)

        // --- ГЕНЕРАЦИЯ КЛАССА ---
        val classContent = """
            package ${args.packageName}
            
            class $className {
                // TODO: Implement logic for $className
            }
        """.trimIndent()

        generateGitIgnore(path)

        writeSourceFile(path, args.packageName, "$className.kt", classContent)

        appendToSettings(args.moduleName)
        println("✅ Created Kotlin module with class $className at: $path")

        addToGitIfRequested(path, args)
    }

    private fun writeSourceFile(
        basePath: String,
        packageName: String,
        fileName: String,
        content: String
    ) {
        val packagePath = packageName.replace(".", "/")
        val fullPath = "$basePath/src/main/java/$packagePath/$fileName"
        writeFile(fullPath, content)
        println("   📝 Generated file: $fileName")
    }

    private fun writeFile(path: String, content: String) {
        val file = File(path)
        file.parentFile?.mkdirs()
        file.writeText(content)
    }

    private fun appendToSettings(moduleName: String) {
        val settingsFile = File("settings.gradle.kts")
        val includeStatement = "include(\"$moduleName\")"

        if (settingsFile.exists()) {
            val currentContent = settingsFile.readText()
            if (!currentContent.contains(includeStatement)) {
                settingsFile.appendText("\n$includeStatement")
                println("   ⚙️ Added to settings.gradle.kts: $includeStatement")
            }
        } else {
            println("⚠️ Error: settings.gradle.kts not found!")
        }
    }

    private fun generateGitIgnore(path: String) {
        val content = "/build"
        writeFile("$path/.gitignore", content)
        println("   📝 Generated file: .gitignore")
    }

    private fun generateProguardRules(path: String) {
        val content = """
            # Add project specific ProGuard rules here.
            # By default, the flags in this file are appended to flags specified
            # in ${'$'}ANDROID_HOME/tools/proguard/proguard-android.txt
            # You can edit the include path and order by changing the consumerProguardFiles
            # directive in build.gradle.kts.
        """.trimIndent()
        writeFile("$path/proguard-rules.pro", content)
        println("   📝 Generated file: proguard-rules.pro")
    }


    private fun generateConsumerRules(path: String) {
        writeFile("$path/consumer-rules.pro", "# Rules for library consumers.")
        println("   📝 Generated file: consumer-rules.pro")
    }

    private fun addToGitIfRequested(path: String, args: InputArgs) {
        // Проверяем, передал ли юзер флаг --git
        if (args.features.contains("--git")) {
            try {
                val process = ProcessBuilder("git", "add", path)
                    .directory(File("."))
                    .start()
                process.waitFor()
                println("   🌿 Added to Git: $path")
            } catch (e: Exception) {
                println("   ⚠️ Failed to add to Git: ${e.message}")
            }
        }
    }
}
