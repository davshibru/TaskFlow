package com.davidshibru.template.script

import java.io.File
import java.util.concurrent.TimeUnit

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

    fun updateFile(path: String, transform: (String) -> String) {
        val file = File(path)
        if (!file.exists()) {
            println("⚠️ Error: $path not found!")
            return
        }

        val currentContent = file.readText()
        val updatedContent = transform(currentContent)

        if (updatedContent != currentContent) {
            file.writeText(updatedContent)
            println("   ✏️ Updated file: $path")
        }
    }

    fun ensureImport(path: String, importLine: String) {
        updateFile(path) { content ->
            if (content.contains(importLine)) return@updateFile content

            val packageMatch = Regex("""^package\s+[^\r\n]+""").find(content) ?: return@updateFile content
            val insertIndex = Regex("""^import\s+[^\r\n]+""", RegexOption.MULTILINE)
                .findAll(content)
                .lastOrNull()
                ?.range
                ?.last
                ?.plus(1)
                ?: packageMatch.range.last + 1

            val prefix = content.substring(0, insertIndex).trimEnd()
            val suffix = content.substring(insertIndex).trimStart('\r', '\n')
            buildString {
                append(prefix)
                append("\n\n")
                append(importLine)
                if (suffix.isNotEmpty()) {
                    append("\n\n")
                    append(suffix)
                } else {
                    append('\n')
                }
            }
        }
    }

    fun insertBeforeIfMissing(
        path: String,
        marker: String,
        textToInsert: String,
        uniqueMarker: String = textToInsert.trim()
    ) {
        updateFile(path) { content ->
            if (content.contains(uniqueMarker)) return@updateFile content

            val index = content.lastIndexOf(marker)
            if (index == -1) return@updateFile content

            val normalizedInsert = textToInsert.trimEnd() + "\n"
            content.substring(0, index) + normalizedInsert + content.substring(index)
        }
    }

    fun insertAfterIfMissing(
        path: String,
        marker: String,
        textToInsert: String,
        uniqueMarker: String = textToInsert.trim()
    ) {
        updateFile(path) { content ->
            if (content.contains(uniqueMarker)) return@updateFile content

            val index = content.indexOf(marker)
            if (index == -1) return@updateFile content

            val insertIndex = index + marker.length
            val normalizedInsert = "\n" + textToInsert.trimEnd()
            content.substring(0, insertIndex) + normalizedInsert + content.substring(insertIndex)
        }
    }

    fun appendIfMissing(
        path: String,
        textToAppend: String,
        uniqueMarker: String = textToAppend.trim()
    ) {
        updateFile(path) { content ->
            if (content.contains(uniqueMarker)) return@updateFile content

            content.trimEnd() + "\n" + textToAppend.trimEnd() + "\n"
        }
    }

    fun insertIntoBlockBeforeClosingBraceIfMissing(
        path: String,
        blockStartMarker: String,
        textToInsert: String,
        uniqueMarker: String = textToInsert.trim()
    ) {
        updateFile(path) { content ->
            if (content.contains(uniqueMarker)) return@updateFile content

            val blockStart = content.indexOf(blockStartMarker)
            if (blockStart == -1) return@updateFile content

            val openingBrace = content.indexOf('{', blockStart)
            if (openingBrace == -1) return@updateFile content

            var depth = 0
            for (index in openingBrace until content.length) {
                when (content[index]) {
                    '{' -> depth++
                    '}' -> {
                        depth--
                        if (depth == 0) {
                            val lineStart = content.lastIndexOf('\n', startIndex = index - 1).let {
                                if (it == -1) 0 else it + 1
                            }
                            val normalizedInsert = textToInsert.trimEnd() + "\n"
                            return@updateFile content.substring(0, lineStart) +
                                normalizedInsert +
                                content.substring(lineStart)
                        }
                    }
                }
            }

            content
        }
    }

    fun addToGitIfRequested(path: String, args: InputArgs) {
        if (args.features.contains("--git")) {
            try {
                val process = ProcessBuilder("git", "add", path)
                    .directory(File("."))
                    .redirectErrorStream(true)
                    .start()
                val finished = process.waitFor(15, TimeUnit.SECONDS)
                if (finished && process.exitValue() == 0) {
                    println("   🌿 Added to Git: $path")
                } else {
                    if (!finished) {
                        process.destroyForcibly()
                        process.waitFor(2, TimeUnit.SECONDS)
                    }
                    val output = process.inputStream.bufferedReader().readText().trim()
                    val reason = output.takeIf { it.isNotEmpty() } ?: "git add timed out or failed"
                    println("   ⚠️ Failed to add to Git: $path ($reason)")
                }
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

    fun moduleSegment(moduleName: String): String = moduleName.substringAfterLast(":")

    fun compactLowerName(moduleName: String): String =
        moduleSegment(moduleName).replace("-", "").replace("_", "").lowercase()

    fun demoFlavorName(moduleName: String): String {
        val flavorName = compactLowerName(moduleName)
        val reservedSourceSetNames = setOf("main", "test", "androidtest")

        return if (flavorName in reservedSourceSetNames) {
            "${flavorName}Feature"
        } else {
            flavorName
        }
    }

    fun snakeCaseName(moduleName: String): String =
        moduleSegment(moduleName).replace("-", "_").replace(".", "_")

    fun lowerCamelName(typeName: String): String =
        typeName.replaceFirstChar { it.lowercase() }
}
