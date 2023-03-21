plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}

subprojects {
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.build.get().dependsOn(tasks.getByName("shadowJar"))
}