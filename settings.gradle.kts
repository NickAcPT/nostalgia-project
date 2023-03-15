rootProject.name = "nostalgia-project"

include(
    // Remote Procedure Call library that handles communication between the services
    ":libraries:nostalgia-rpc",

    // Abstractions implementing various gameplay mechanics
    ":abstractions:nostalgia-economy",
    ":abstractions:nostalgia-mines",

    // The logic itself that handles all player stuff
    ":abstractions:nostalgia-core",

    // The various plugins for the different platforms
    ":plugins:nostalgia-bukkit",
    ":plugins:nostalgia-velocity",

    // CLI tool to deploy nostalgia servers
    ":tools:nostalgia-cli",
)

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("kotlin-coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.0-Beta")
            library("kotlin-reflect", "org.jetbrains.kotlin:kotlin-reflect:1.8.10")
            library("kryo", "com.esotericsoftware:kryo:5.4.0")
            library("guava", "com.google.guava:guava:31.1-jre")

            library("kotlin-test", "org.jetbrains.kotlin:kotlin-test:1.8.0")
        }
    }
}
