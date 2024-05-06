plugins {
    java
    id("org.cyclonedx.bom") version "1.8.1"
}

repositories {
    mavenCentral()
}

import org.cyclonedx.gradle.CycloneDxTask

tasks.withType<CycloneDxTask> {
    includeConfigs = setOf("runtimeClasspath")
    projectType = "application"
    schemaVersion = "1.5"
    destination = file("build/reports")
    outputName = "bom"
    outputFormat = CycloneDxTask.OutputFormat.XML
    includeBomSerialNumber = false
    includeLicenseText = false
    componentVersion = "2.0.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
