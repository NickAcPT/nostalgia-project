
rootProject.name = "nostalgia-project"

include(":libraries:nostalgia-rpc")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("kotlin-coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.0-Beta")
            library("kryo", "com.esotericsoftware:kryo:5.4.0")
            library("guava", "com.google.guava:guava:31.1-jre")

            library("kotlin-test", "org.jetbrains.kotlin:kotlin-test:1.8.0")
            library("mockk", "io.mockk:mockk:1.13.4")
        }
    }
}
