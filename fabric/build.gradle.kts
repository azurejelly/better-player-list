plugins {
    alias(libs.plugins.loom)
}

base {
    archivesName.set("${project.property("archive_base_name")}-fabric")
}

loom {
    runs {
        named("client") {
            client()
            ideConfigGenerated(true)
            runDir("run")
            configName = "Fabric/Client"
        }
    }
}

dependencies {
    minecraft(libs.minecraft)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(project(":common"))
    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)
    implementation(libs.modmenu)

    // TODO: uncomment devauth once it is updated to 26.1
    // runtimeOnly(libs.devauth.fabric)
}

tasks {
    jar {
        from(sourceSets["main"].output)
        from(project(":common").sourceSets["main"].output.classesDirs)
    }

    processResources {
        filteringCharset = "UTF-8"
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from(project(":common").sourceSets.main.get().resources)

        // TODO: update minecraft version range on fabric.mod.json once 26.1 releases
        filesMatching("fabric.mod.json") {
            expand(
                "version" to project.version,
                "minecraft_version" to libs.versions.minecraft.get(),
                "loader_version" to libs.versions.fabric.loader.get(),
                "mod_id" to rootProject.property("mod_id").toString(),
                "mod_name" to rootProject.property("mod_name").toString(),
                "mod_description" to rootProject.property("mod_description").toString(),
                "license" to rootProject.property("license").toString()
            )
        }
    }
}