plugins {
    id("fabric-loom") version "1.14-SNAPSHOT"
    id("maven-publish")
}

val minecraftVersion = "${project.findProperty("minecraft_version")}"
val archiveBaseName = "${project.findProperty("archive_base_name")}"

version = "${project.version}+${minecraftVersion}"

base {
    archivesName.set(archiveBaseName)
}

repositories {
    maven {
        name = "DevAuth"
        url = uri("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
    }
    maven {
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings("net.fabricmc:yarn:${project.findProperty("yarn_mappings")}:v2")

    modImplementation("com.terraformersmc:modmenu:${project.findProperty("modmenu_version")}")
    modImplementation("net.fabricmc:fabric-loader:${project.findProperty("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.findProperty("fabric_version")}")
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", project.property("minecraft_version"))
    inputs.property("loader_version", project.property("loader_version"))

    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            mapOf(
                "version" to project.version,
                "minecraft_version" to project.property("minecraft_version"),
                "loader_version" to project.property("loader_version")
            )
        )
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

        // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
        // if it is present.
        // If you remove this line, sources will not be generated.
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

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = archiveBaseName
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}
