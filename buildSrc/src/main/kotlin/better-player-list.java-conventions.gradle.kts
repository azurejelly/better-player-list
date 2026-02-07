plugins {
    id("java")
}

val targetJavaVersion = 25

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
    }

    java {
        withSourcesJar()

        sourceCompatibility = JavaVersion.toVersion(targetJavaVersion)
        targetCompatibility = JavaVersion.toVersion(targetJavaVersion)
    }
}