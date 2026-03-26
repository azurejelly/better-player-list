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
    implementation(project(":common"))

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)
    implementation(libs.modmenu)

    runtimeOnly(libs.devauth.fabric)
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