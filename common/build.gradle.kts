dependencies {
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