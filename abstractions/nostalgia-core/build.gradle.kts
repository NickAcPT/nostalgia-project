plugins {
    `java-library`
}

dependencies {
    api(libs.adventure.api)

    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.junit)
    testImplementation(libs.mockk)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}