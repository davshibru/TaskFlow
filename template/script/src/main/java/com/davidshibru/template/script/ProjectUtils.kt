package com.davidshibru.template.script

import java.io.File

object ProjectUtils {

    fun writeFile(path: String, content: String) {
        val file = File(path)
        file.parentFile?.mkdirs()
        file.writeText(content)
    }

    fun writeSourceFile(basePath: String, packageName: String, fileName: String, content: String) {
        val packagePath = packageName.replace(".", "/")
        val fullPath = "$basePath/src/main/java/$packagePath/$fileName"
        writeFile(fullPath, content)
        println("   📝 Generated file: $fileName")
    }

    fun appendToSettings(moduleName: String) {
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

    fun addToGitIfRequested(path: String, args: InputArgs) {
        if (args.features.contains("--git")) {
            try {
                val process = ProcessBuilder("git", "add", path).directory(File(".")).start()
                process.waitFor()
                println("   🌿 Added to Git: $path")
            } catch (e: Exception) {
                println("   ⚠️ Failed to add to Git: ${e.message}")
            }
        }
    }

    fun ensureParentBuildFilesExist(basePath: String, args: InputArgs) {
        val parts = basePath.split("/")
        var currentPath = ""

        for (i in 0 until parts.size - 1) {
            currentPath = if (currentPath.isEmpty()) parts[i] else "$currentPath/${parts[i]}"
            val parentBuildFile = File("$currentPath/build.gradle.kts")

            if (!parentBuildFile.exists()) {
                File(currentPath).mkdirs()
                parentBuildFile.writeText(CodeTemplates.parentBuildGradle())
                println("   📁 Created parent build.gradle.kts in $currentPath")
                addToGitIfRequested("$currentPath/build.gradle.kts", args)
            }
        }
    }

    fun toTypeSafeAccessor(moduleName: String): String {
        val formatted = moduleName.removePrefix(":").split(":").joinToString(".") { part ->
            part.split("-", "_").mapIndexed { index, s ->
                if (index == 0) s else s.replaceFirstChar { it.uppercase() }
            }.joinToString("")
        }
        return "projects.$formatted"
    }
}