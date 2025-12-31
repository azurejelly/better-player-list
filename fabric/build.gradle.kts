plugins {
    alias(libs.plugins.loom)
    id("maven-publish")
}

base {
    archivesName.set("${project.property("archive_base_name")}-fabric")
}

dependencies {
    include(project(":common"))
    implementation(project(":common"))

    minecraft(libs.minecraft)
    mappings(
        variantOf(libs.fabric.yarn) {
            classifier("v2")
        }
    )

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.modmenu)
    modRuntimeOnly(libs.devauth.fabric)
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", libs.versions.minecraft.get())
    inputs.property("loader_version", libs.versions.fabric.loader.get())

    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            mapOf(
                "version" to project.version,
                "minecraft_version" to libs.versions.minecraft.get(),
                "loader_version" to libs.versions.fabric.loader.get(),
                "mod_id" to rootProject.property("mod_id"),
                "mod_name" to rootProject.property("mod_name"),
                "mod_description" to rootProject.property("mod_description"),
                "license" to rootProject.property("license")
            )
        )
    }
}

