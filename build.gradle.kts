plugins {
    id 'java'
    id 'org.springframework.boot' version '2.7.7'
    id 'io.spring.dependency-management' version '1.0.15.RELEASE'
    id 'org.sonarqube' version '2.7'
    id 'org.cyclonedx.bom' version '1.8.2'
}

repositories {
    mavenCentral()
}
cyclonedxBom {
 includeConfigs = ["runtimeClasspath"]
 projectType = "application"
 schemaVersion = "1.5"
 destination = file("build/reports")
 outputName = "bom"
 outputFormat = "xml"
 includeBomSerialNumber = false
 includeLicenseText = false
 componentVersion = "2.0.0"
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
