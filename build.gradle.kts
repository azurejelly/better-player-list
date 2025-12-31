import org.gradle.internal.extensions.stdlib.toDefaultLowerCase

plugins {
    id("java")
    id("maven-publish")
}

val minecraftVersion = libs.versions.minecraft.get()
val archiveBaseName = "${project.findProperty("archive_base_name")}"

group = "dev.azuuure"
version = project.version

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    group = "dev.azuuure.playerlist"
    version = "${rootProject.version}+${minecraftVersion}"

    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            name = "Terraformers"
            url = uri("https://maven.terraformersmc.com/")
        }

        maven {
            name = "DevAuth"
            url = uri("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
        }
    }

    tasks {
        val targetJavaVersion = 21

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

        jar {
            from("LICENSE") {
                rename { fileName ->
                    "${fileName}_${archiveBaseName}"
                }
            }
        }
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                artifactId = archiveBaseName
                from(components["java"])
            }
        }

        repositories {
            mavenLocal()
            maven {
                val repository = if (version.toString().toDefaultLowerCase().contains("snapshot")) "snapshots" else "releases"

                name = "azurejelly"
                url = uri("https://repo.azuuure.dev/maven-$repository/")
                credentials(PasswordCredentials::class)
            }
        }
    }
}