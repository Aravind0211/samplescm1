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
    stage('Publish'){
      steps{
        nexusArtifactUploader artifacts:[[artifactId: 'scm1',classifier:'',file:'/home/logicfocus/.jenkins/workspace/Aravind/scm1/build/libs/gradle-build-scan-quickstart.jar',type:'jar']]credentialsId:'nexus1',groupId: 'com.logicfocus',nexusUrl: '192.168.1.30:8081',nexusVersion: 'nexus3',protocol: 'http',repository: 'scm1',Version: '1.0.1'
      }
    }
  }
}
