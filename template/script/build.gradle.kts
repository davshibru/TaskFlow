plugins {
    kotlin("jvm") version "2.2.21"
    application
}

application {
    mainClass.set("com.davidshibru.template.script.MainKt")
}

tasks.named<JavaExec>("run") {
    workingDir = rootDir

    systemProperty("file.encoding", "UTF-8")
    systemProperty("sun.stdout.encoding", "UTF-8")
    systemProperty("sun.stderr.encoding", "UTF-8")
}