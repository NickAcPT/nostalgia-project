plugins {
    kotlin("kapt")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    kapt("com.velocitypowered:velocity-api:3.1.1")

    implementation(project(":abstractions:nostalgia-core"))
}