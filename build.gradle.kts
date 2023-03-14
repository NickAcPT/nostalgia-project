import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    kotlin("jvm") version "1.8.0" apply false
}

subprojects {
    group = "io.github.nickacpt.nostalgia"
    version = "1.0-SNAPSHOT"

    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
    }

    dependencies {
        "implementation"(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    }

    extensions.getByType<KotlinJvmProjectExtension>().apply {
        jvmToolchain(18)
    }
}