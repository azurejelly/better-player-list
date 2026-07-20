val minecraft: String = libs.versions.minecraft.get()

subprojects {
    apply(plugin = "better-player-list.java-conventions")
    apply(plugin = "better-player-list.publishing-conventions")

    version = "${rootProject.version}+${minecraft}"

    repositories {
        mavenCentral()

        maven {
            name = "azurejelly"
            url = uri("https://repo.azuuure.dev/repository/maven-public/")
        }

        maven {
            name = "gnomecraft"
            url = uri("https://maven.gnomecraft.net/repository/maven-terraformers")
        }

        maven {
            name = "DevAuth"
            url = uri("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
        }
    }
}
