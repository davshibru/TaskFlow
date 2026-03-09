package com.davidshibru.template.script

data class InputArgs(
    val templateName: String,     // например: kotlin-library, android-library, feature
    val moduleName: String,       // например: :core:essentials
    val packageName: String,      // например: com.davidshibru.taskflow.core.essentials
    val features: List<String>    // например: [--compose, --hilt, --retrofit]
)

fun main(args: Array<String>) {
    val inputArgs = parseArgs(args)
    val generator = TemplateGeneratorImpl()
    generator.generate(inputArgs)
}

private fun parseArgs(args: Array<String>): InputArgs {
    if (args.size < 3) error("Нужно минимум 3 аргумента: <template> <module> <package>")

    // Все аргументы, начиная с 4-го (индекс 3), считаем флагами
    val flags = if (args.size > 3) args.drop(3) else emptyList()

    return InputArgs(
        templateName = args[0],
        moduleName = args[1],
        packageName = args[2],
        features = flags
    )
}