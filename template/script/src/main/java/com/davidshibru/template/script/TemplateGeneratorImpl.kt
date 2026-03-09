package com.davidshibru.template.script

import java.io.File

class TemplateGeneratorImpl {
    fun generate(args: InputArgs) {
        val basePath = args.moduleName.replace(":", "/").removePrefix("/")

        val featureName =
            args.moduleName.substringAfterLast(":").replaceFirstChar { it.uppercase() }

        when (args.templateName) {
            "feature" -> generateFeatureModule(basePath, featureName, args)
            "kotlin-library" -> generateKotlinLibrary(basePath, args, emptyList())
            "android-library" -> generateAndroidLibrary(basePath, args, emptyList())
            else -> println("❌ Неизвестный шаблон: ${args.templateName}")
        }
    }

    private fun generateFeatureModule(basePath: String, featureName: String, args: InputArgs) {
        println("🚀 Создаем фича-модуль: $featureName...")

        val domainModuleName = "${args.moduleName}:domain"
        val domainPackageName = "${args.packageName}.domain"

        // --- 1. Создаем Domain ---
        val domainArgs = args.copy(
            moduleName = domainModuleName,
            packageName = domainPackageName,
            features = args.features.filter { it != "--hilt" && it != "--compose" }
        )
        generateKotlinLibrary("$basePath/domain", domainArgs)

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
            "androidTestImplementation(libs.androidx.espresso.core)"
        )

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

        val manifestContent = """<?xml version="1.0" encoding="utf-8"?><manifest />"""
        writeFile("$path/src/main/AndroidManifest.xml", manifestContent)

        appendToSettings(args.moduleName)
        println("✅ Создан Android модуль в $path")
    }

    private fun generateKotlinLibrary(
        path: String,
        args: InputArgs,
        extraDependencies: List<String> = emptyList()
    ) {
        val depsBlock = if (extraDependencies.isNotEmpty()) {
            "\ndependencies {\n    ${extraDependencies.joinToString("\n    ")}\n}"
        } else ""

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
        $depsBlock
    """.trimIndent()

        writeFile("$path/build.gradle.kts", buildGradleContent)
        appendToSettings(args.moduleName)
        println("✅ Создан Kotlin модуль в $path")
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
        println("   📝 Сгенерирован файл: $fileName")
    }

    private fun writeFile(path: String, content: String) {
        val file = File(path)
        file.parentFile?.mkdirs()
        file.writeText(content)
    }

    private fun appendToSettings(moduleName: String) {
        // Предполагается, что скрипт запускается из корня проекта
        val settingsFile = File("settings.gradle.kts")
        val includeStatement = "include(\"$moduleName\")"

        if (settingsFile.exists()) {
            val currentContent = settingsFile.readText()
            if (!currentContent.contains(includeStatement)) {
                settingsFile.appendText("\n$includeStatement")
                println("   ⚙️ Добавлено в settings.gradle.kts: $includeStatement")
            }
        } else {
            println("⚠️ Файл settings.gradle.kts не найден в корне проекта!")
        }
    }
}
