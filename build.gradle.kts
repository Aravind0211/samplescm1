plugins {
    id 'java'
    id 'org.springframework.boot' version '2.7.7'
    id 'io.spring.dependency-management' version '1.0.15.RELEASE'
    id 'org.sonarqube' version '2.7'
    id 'org.cyclonedx.bom' version '1.8.2'
}
apply plugin: 'java'
apply plugin: 'maven-publish'
group = 'com.logicfocus'
version = '2.0'
sourceCompatibility = 1.8 // java 8
targetCompatibility = 1.8
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
  implementation 'com.google.guava:guava:29.0-jre'
}
java {
	withSourcesJar()
	withJavadocJar()
}
publishing {
    publications {
        maven(MavenPublication) {
		from components.java
        }
    }
}
wrapper {
    gradleVersion = "7.3.3"
    distributionType = Wrapper.DistributionType.ALL
}
