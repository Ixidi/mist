plugins {
    kotlin("jvm") version Versions.kotlin apply false
    id("com.github.johnrengelman.shadow") version "5.2.0" apply false
}

allprojects {
    group = Project.group
    version = Project.version
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
        spigot()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

