plugins {
    `java-library`
}

dependencies {
    api("net.kyori:adventure-api:4.12.0")

    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockk)
}