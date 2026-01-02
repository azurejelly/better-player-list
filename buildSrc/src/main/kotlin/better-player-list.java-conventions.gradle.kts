plugins {
    id("java")
}

val targetJavaVersion = 21

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
    }

    java {
        if (JavaVersion.current() < JavaVersion.toVersion(targetJavaVersion)) {
            toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
        }

        withSourcesJar()
    }
}