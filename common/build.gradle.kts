plugins {
    alias(libs.plugins.neoforge.moddev)
    `java-library`
}

neoForge {
    neoFormVersion = "26.1-snapshot-7-1"
}

dependencies {
    compileOnly(libs.mixin)
    compileOnly(libs.asm)
    compileOnly(libs.lombok)
    compileOnly(libs.slf4j)
    annotationProcessor(libs.lombok)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.launcher)
}

tasks {
    test {
        useJUnitPlatform()
    }
}