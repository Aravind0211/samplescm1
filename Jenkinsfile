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
  }
}
