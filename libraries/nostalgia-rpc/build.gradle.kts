dependencies {
    implementation(libs.kryo)
    implementation(libs.guava)
    implementation(libs.kotlin.coroutines)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockk)
}

tasks.test {
    useJUnitPlatform()
}