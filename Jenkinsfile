pipeline{
  agent any 
  stages{
    stage('Build'){
      steps{
        sh './gradlew build'
      }
    }
    stage('Test'){
      tools{
        jdk 'java-8'
      }
      steps{
        sh './gradlew test'
      }
    }
    stage("Static Analysis") {
      tools {
        jdk 'java-8'
      }
      environment {
        scannerHome= tool 'SonarQube Scanner 4.8.0.2856'
        projectName= "scm1"
      }
      steps {
        withSonarQubeEnv('Sonar_12') {
        sh '''${scannerHome}/bin/sonar-scanner -Dsonar.java.binaries=build/classes/java \
        -Dsonar.projectKey=$projectName -Dsonar.sources=.'''
        }
      }
    }
  }
}
