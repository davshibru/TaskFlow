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
        val presentationPackageName = "${args.packageName}.presentation"
        val screenFunctionName = "${ProjectUtils.lowerCamelName(featureName)}Screen"

        // --- 1. ДОМЕННЫЙ СЛОЙ (DOMAIN) ---
        val domainModuleName = "${args.moduleName}:domain"
        val domainPackageName = "${args.packageName}.domain"
        val forDomainArgs = args.features.toMutableList()
        forDomainArgs.add("--ksp")
        val domainArgs = args.copy(
            moduleName = domainModuleName,
            packageName = domainPackageName,
            features = forDomainArgs.filter { it != "--hilt" && it != "--compose" }
        )

        generateKotlinLibrary(
            path = "$basePath/domain",
            className = "DomainMarker", // Временный маркер, можно удалить
            args = domainArgs,
            predefinedDependencies = listOf(
                "implementation(projects.core.essentials)",
                "implementation(libs.hilt.core)",
                "ksp(libs.hilt.compiler)",
                "implementation(libs.javax.inject)",
            ),
        )
        // Удаляем маркерный класс, так как мы сгенерируем структуру вручную
        File(
            "$basePath/domain/src/main/java/${
                domainPackageName.replace(
                    ".",
                    "/"
                )
            }/DomainMarker.kt"
        ).delete()

        // Генерируем структуру папок как на скриншоте
        ProjectUtils.writeSourceFile(
            "$basePath/domain", "$domainPackageName.entities", "${featureName}Entity.kt",
            CodeTemplates.entityClass("$domainPackageName.entities", featureName)
        )

        ProjectUtils.writeSourceFile(
            "$basePath/domain", "$domainPackageName.exceptions.base", "${featureName}Exception.kt",
            CodeTemplates.exceptionClass("$domainPackageName.exceptions.base", featureName)
        )

        ProjectUtils.writeSourceFile(
            "$basePath/domain", "$domainPackageName.repositories", "${featureName}Repository.kt",
            CodeTemplates.repositoryInterface("$domainPackageName.repositories", featureName)
        )

        ProjectUtils.writeSourceFile(
            "$basePath/domain", "$domainPackageName.usecases", "${featureName}UseCase.kt",
            CodeTemplates.useCaseInterface("$domainPackageName.usecases", featureName)
        )

        // Папка resources пока просто создается пустой
        File(
            "$basePath/domain/src/main/java/${
                domainPackageName.replace(
                    ".",
                    "/"
                )
            }/resources"
        ).mkdirs()


        // --- 2. СЛОЙ ПРЕДСТАВЛЕНИЯ (PRESENTATION) ---
        val presentationFeatures = (args.features + listOf("--compose", "--hilt", "--navigation")).distinct()
        val presentationArgs = args.copy(
            moduleName = "${args.moduleName}:presentation",
            packageName = presentationPackageName,
            features = presentationFeatures
        )
        val domainAccessor = toTypeSafeAccessor(domainModuleName)

        generateAndroidLibrary(
            path = "$basePath/presentation",
            className = null,
            args = presentationArgs,
            extraPlugins = listOf("alias(libs.plugins.convention.serialization)"),
            androidConfigLines = listOf(
                "resourcePrefix = \"${ProjectUtils.snakeCaseName(args.moduleName)}_\""
            ),
            predefinedDependencies = listOf(
                "implementation($domainAccessor)",
                "implementation(projects.core.essentials)",
                "implementation(projects.core.theme)",
                "implementation(projects.core.presentation)",
                "implementation(projects.core.navigationDsl)"
            ),
            extraDependencies = listOf(
                "testImplementation(projects.core.presentationTest)",
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
            CodeTemplates.screenClass(
                packageName = presentationArgs.packageName,
                featureName = featureName,
                screenFunctionName = screenFunctionName
            )
        )

        ProjectUtils.addToGitIfRequested("$basePath/presentation/src/main/java", args)


        // --- 3. ДЕМО СЛОЙ (DEMO) ---
        println("🚀 Creating demo module for: $featureName...")
        val demoModuleName = "${args.moduleName}:demo"
        val demoPackageName = "${args.packageName}.demo"
        val demoArgs = args.copy(
            moduleName = demoModuleName,
            packageName = demoPackageName,
            // Демо модулю обычно нужен Hilt для DI и, возможно, корутины для delay
            features = (args.features + "--hilt").filter { it != "--coroutines" }.distinct()
        )

        generateAndroidLibrary(
            path = "$basePath/demo",
            className = null,
            args = demoArgs,
            predefinedDependencies = listOf(
                "implementation($domainAccessor)",
                "implementation(projects.core.essentials)"
            )
        )

        // Генерируем фейковый репозиторий
        ProjectUtils.writeSourceFile(
            "$basePath/demo", demoPackageName, "Demo${featureName}Repository.kt",
            CodeTemplates.demoRepositoryClass(demoPackageName, domainPackageName, featureName)
        )

        // Генерируем Hilt модуль для подмены репозитория в песочнице
        ProjectUtils.writeSourceFile(
            "$basePath/demo", demoPackageName, "${featureName}DemoModule.kt",
            CodeTemplates.demoHiltModule(demoPackageName, domainPackageName, featureName)
        )

        updateCoreNavigation(
            featureName = featureName,
            moduleName = args.moduleName,
            presentationPackageName = presentationPackageName,
            screenFunctionName = screenFunctionName,
            args = args
        )
        updateAppDemo(
            featureName = featureName,
            moduleName = args.moduleName,
            presentationPackageName = presentationPackageName,
            screenFunctionName = screenFunctionName,
            args = args
        )

        ProjectUtils.addToGitIfRequested(basePath, args)

        println("✅ Feature $featureName generated successfully with Domain, Presentation, and Demo modules!")
    }

    private fun generateAndroidLibrary(
        path: String,
        className: String? = null,
        args: InputArgs,
        extraPlugins: List<String> = emptyList(),
        androidConfigLines: List<String> = emptyList(),
        predefinedDependencies: List<String> = emptyList(),
        extraDependencies: List<String> = emptyList()
    ) {
        ProjectUtils.ensureParentBuildFilesExist(path, args)
        val plugins = mutableListOf("alias(libs.plugins.convention.android.library)")

        if (args.features.contains("--compose")) plugins.add("alias(libs.plugins.convention.compose)")
        if (args.features.contains("--hilt")) plugins.add("alias(libs.plugins.convention.hilt)")
        plugins.addAll(extraPlugins)

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
                ${androidConfigLines.joinToString("\n                ")}
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
        val plugins = mutableListOf(
            "id(\"java-library\")",
            "alias(libs.plugins.jetbrains.kotlin.jvm)"
        )

        if (args.features.contains("--ksp")) plugins.add("alias(libs.plugins.ksp)")

        val allDeps =
            predefinedDependencies + listOf("testImplementation(libs.junit)") + extraDependencies

        val buildGradle = """
            plugins {
                ${plugins.joinToString("\n                ")}
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

    private fun updateCoreNavigation(
        featureName: String,
        moduleName: String,
        presentationPackageName: String,
        screenFunctionName: String,
        args: InputArgs
    ) {
        val routeName = "${featureName}Route"
        val presentationAccessor = toTypeSafeAccessor("${moduleName}:presentation")

        ProjectUtils.insertAfterIfMissing(
            path = "core/navigation/build.gradle.kts",
            marker = "dependencies {",
            textToInsert = "    implementation($presentationAccessor)",
            uniqueMarker = "implementation($presentationAccessor)"
        )

        ProjectUtils.insertBeforeIfMissing(
            path = "core/navigation/src/main/java/com/davidshibru/taskflow/core/navigation/Route.kt",
            marker = "",
            textToInsert = "\n@kotlinx.serialization.Serializable\ndata object $routeName : Route",
            uniqueMarker = "data object $routeName : Route"
        )

        ProjectUtils.ensureImport(
            path = "core/navigation/src/main/java/com/davidshibru/taskflow/core/navigation/AppNavGraph.kt",
            importLine = "import $presentationPackageName.$screenFunctionName"
        )
        ProjectUtils.insertBeforeIfMissing(
            path = "core/navigation/src/main/java/com/davidshibru/taskflow/core/navigation/AppNavGraph.kt",
            marker = "}",
            textToInsert = "    composable<$routeName> { $screenFunctionName() }",
            uniqueMarker = "composable<$routeName> { $screenFunctionName() }"
        )

        val routerFilePath =
            "core/navigation/src/main/java/com/davidshibru/taskflow/core/navigation/routers/${featureName}RouterImpl.kt"
        ProjectUtils.writeFile(
            routerFilePath,
            CodeTemplates.appRouterClass(
                packageName = "com.davidshibru.taskflow.core.navigation.routers",
                presentationPackageName = presentationPackageName,
                featureName = featureName
            )
        )
        ProjectUtils.addToGitIfRequested(routerFilePath, args)

        val routersModulePath =
            "core/navigation/src/main/java/com/davidshibru/taskflow/core/navigation/di/RoutersModule.kt"
        ProjectUtils.ensureImport(
            path = routersModulePath,
            importLine = "import com.davidshibru.taskflow.core.navigation.routers.${featureName}RouterImpl"
        )
        ProjectUtils.ensureImport(
            path = routersModulePath,
            importLine = "import $presentationPackageName.${featureName}Router"
        )
        ProjectUtils.insertBeforeIfMissing(
            path = routersModulePath,
            marker = "}",
            textToInsert = """
                
                    @Binds
                    fun bind${featureName}Router(
                        ${ProjectUtils.lowerCamelName(featureName)}RouterImpl: ${featureName}RouterImpl,
                    ): ${featureName}Router
            """.trimIndent(),
            uniqueMarker = "fun bind${featureName}Router("
        )
    }

    private fun updateAppDemo(
        featureName: String,
        moduleName: String,
        presentationPackageName: String,
        screenFunctionName: String,
        args: InputArgs
    ) {
        val flavorName = ProjectUtils.compactLowerName(moduleName)
        val presentationAccessor = toTypeSafeAccessor("${moduleName}:presentation")
        val demoAccessor = toTypeSafeAccessor("${moduleName}:demo")
        val appDemoBuildGradle = "app-demo/build.gradle.kts"

        ProjectUtils.insertBeforeIfMissing(
            path = appDemoBuildGradle,
            marker = "    defaultConfig {",
            textToInsert = """
                create("$flavorName") {
                    dimension = "feature"
                    applicationIdSuffix = ".$flavorName"
                }
            """.trimIndent().prependIndent("        "),
            uniqueMarker = "create(\"$flavorName\")"
        )

        ProjectUtils.insertBeforeIfMissing(
            path = appDemoBuildGradle,
            marker = "    implementation(libs.androidx.core.ktx)",
            textToInsert = """
                "${flavorName}Implementation"($presentationAccessor)
                "${flavorName}Implementation"($demoAccessor)
            """.trimIndent().prependIndent("    "),
            uniqueMarker = "\"${flavorName}Implementation\"($presentationAccessor)"
        )

        val demoBasePath = "app-demo/src/$flavorName/java/com/davidshibru/taskflow/demo"
        val demoRouterPath = "$demoBasePath/Demo${featureName}Router.kt"
        val demoModulePath = "$demoBasePath/DemoNavigationModule.kt"
        val demoScreenPath = "$demoBasePath/DemoScreenConfig.kt"

        ProjectUtils.writeFile(
            demoRouterPath,
            CodeTemplates.demoRouterClass(
                presentationPackageName = presentationPackageName,
                featureName = featureName
            )
        )
        ProjectUtils.writeFile(
            demoModulePath,
            CodeTemplates.demoNavigationModule(
                presentationPackageName = presentationPackageName,
                featureName = featureName
            )
        )
        ProjectUtils.writeFile(
            demoScreenPath,
            CodeTemplates.demoScreenConfig(
                presentationPackageName = presentationPackageName,
                featureName = featureName,
                screenFunctionName = screenFunctionName
            )
        )

        ProjectUtils.addToGitIfRequested(demoRouterPath, args)
        ProjectUtils.addToGitIfRequested(demoModulePath, args)
        ProjectUtils.addToGitIfRequested(demoScreenPath, args)
    }

    private fun getClassName(args: InputArgs): String {
        return args.moduleName.substringAfterLast(":").split("-", "_").joinToString("") {
            it.replaceFirstChar { char -> char.uppercase() }
        }
    }
}
