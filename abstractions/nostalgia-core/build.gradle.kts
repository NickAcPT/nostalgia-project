plugins {
    `java-library`
}

dependencies {
    api(libs.adventure.api)
    api(project(":libraries:nostalgia-rpc"))

    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.junit)
    testImplementation(libs.mockk)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}