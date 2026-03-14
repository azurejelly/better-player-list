plugins {
    alias(libs.plugins.neoforge.moddev)
    id("idea")
}

repositories {
    maven("https://maven.neoforged.net/releases/")
}

dependencies {
    implementation(project(":common"))

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    runtimeOnly(libs.devauth.neoforge)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

base {
    archivesName.set("${rootProject.property("archive_base_name")}-neoforge")
}

neoForge {
    version = libs.versions.neoforge.main.get()

    runs {
        create("Client") {
            client()
            ideName = "NeoForge/Client (:${project.name})" // the inconsistency was pissing me off
        }
    }

    mods {
        create("${rootProject.property("mod_id")}") {
            sourceSet(sourceSets["main"])
            sourceSet(project(":common").sourceSets["main"])
        }
    }
}

sourceSets {
    named("main") {
        resources.srcDir("src/generated/resources")
    }
}

tasks {
    jar {
        from(sourceSets["main"].output)
        from(project(":common").sourceSets["main"].output)
    }
}

val generateModMetadata by tasks.registering(ProcessResources::class) {
    val replaceProperties = mapOf(
        "mod_id"                  to rootProject.property("mod_id"),
        "mod_name"                to rootProject.property("mod_name"),
        "mod_description"         to rootProject.property("mod_description"),
        "mod_license"             to rootProject.property("license"),
        "mod_version"             to project.version.toString(),
        "minecraft_version"       to libs.versions.minecraft.get(),
        "minecraft_version_range" to "[${libs.versions.minecraft.get()},)",
        "loader_version_range"    to "[${libs.versions.neoforge.loader.get()},)",
        "neoforge_version_range"  to "[${libs.versions.neoforge.main.get()},)"
    )

    inputs.properties(replaceProperties)
    expand(replaceProperties)

    from("src/main/templates")
    into(layout.buildDirectory.dir("generated/sources/modMetadata"))
}

sourceSets.named("main") {
    resources.srcDir(generateModMetadata)
}

neoForge.ideSyncTask(generateModMetadata)

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
