pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }

        maven {
            name = "NeoForged"
            url = uri("https://maven.neoforged.net/releases")
        }

        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

listOf("fabric", "neoforge", "common").forEach {
    include(":$it")
}