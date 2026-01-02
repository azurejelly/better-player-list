val minecraft = libs.versions.minecraft.get()

subprojects {
    apply(plugin = "better-player-list.java-conventions")
    apply(plugin = "better-player-list.publishing-conventions")

    version = "${rootProject.version}+${minecraft}"

    repositories {
        mavenLocal()
        mavenCentral()

        maven {
            name = "azurejelly"
            url = uri("https://repo.azuuure.dev/maven-public/")
        }

        maven {
            name = "Terraformers"
            url = uri("https://maven.terraformersmc.com/")
        }

        maven {
            name = "DevAuth"
            url = uri("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
        }
    }
}
