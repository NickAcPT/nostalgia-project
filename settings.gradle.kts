
rootProject.name = "nostalgia-project"

include(":libraries:nostalgia-rpc")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("kotlin-coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-Beta")
            library("kryo", "com.esotericsoftware:kryo:5.4.0")
        }
    }
}
