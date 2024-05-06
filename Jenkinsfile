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
        nexusArtifactUploader artifacts: [[artifactId: 'scm1',classifier: '',file:'/home/logicfocus/.jenkins/workspace/Aravind/scm1/build/libs/gradle-build-scan-quickstart.jar',type:'jar']] ,
                                           credentialsId: 'nexus1',
                                           groupId: 'com.logicfocus',
                                           nexusUrl: '192.168.1.30:8081' ,
                                           nexusVersion: 'nexus3',
                                           protocol: 'http',
                                           repository: 'scm1',
                                           version: '1.0.1'
      }
    }
    stage('Read Build Info') {
      environment {
                NEXUS_URL = 'http://192.168.1.30:8081'
                REPO_NAME = 'scm1'
                GROUP_ID = 'com.logicfocus'
                ARTIFACT_ID = 'scm1'
                
    } 
    steps {
     script {
                    def artifactPath = "/${REPO_NAME}/${GROUP_ID.replace('.', '/')}/${ARTIFACT_ID}/${VERSION}/${ARTIFACT_ID}-${VERSION}.jar"
                    def nexusChecksumUrl = "${NEXUS_URL}/service/rest/v1/components?repository=${REPO_NAME}&group=${GROUP_ID}&name=${ARTIFACT_ID}&version=${VERSION}"
                    def artifactInfo = sh(script: "curl -s -u admin:lfadmin ${nexusChecksumUrl}", returnStdout: true).trim()
                    def checksumValue = readJSON(text: artifactInfo).items[0].assets[0].checksum.sha1

                    sh """
                        echo '{' > buildData.json
                        echo '  "buildnumber": "${BUILD_NUMBER}",' >> buildData.json
                        echo '  "buildurl": "${JOB_URL}",' >> buildData.json
                        echo '  "group": "com.logicfocus",' >> buildData.json
                        echo '  "checksum": "${checksumValue}",' >> buildData.json
                        echo '  "artifact": "${ARTIFACT_ID}",' >> buildData.json
                        echo '  "ext": "jar",' >> buildData.json
                        echo '  "version": "${VERSION}"' >> buildData.json
                        echo '}' >> buildData.json
                    """
                    stash name: 'buildDataStash', includes: 'buildData.json'
                    unstash 'buildDataStash'
                    def buildData = readFile('buildData.json')
                    echo "Build Data: ${buildData}"
                    archiveArtifacts artifacts: 'buildData.json', onlyIfSuccessful: false
        }
      }
    }
  }
}
