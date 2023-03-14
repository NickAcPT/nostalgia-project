dependencies {
    implementation(libs.kryo)
    implementation(libs.guava)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.reflect)

    testImplementation(libs.kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}