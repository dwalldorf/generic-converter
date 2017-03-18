pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh './gradlew clean test'
      }
    }
    stage('Build') {
      steps {
        parallel(
          "Build sources": {
            sh './gradlew sourcesJar'
          },
          "Build javadoc": {
            sh './gradlew javadocJar'
          },
          "Build lib": {
            sh './gradlew jar'
          }
        )
      }
    }
    stage('Artifacts') {
      steps {
        parallel(
          "Fingerprint": {
            fingerprint 'build/libs/*.jar'
          },
          "Publish test results": {
            junit 'build/test-results/**/*.xml'
          },
          "Archive": {
            archiveArtifacts 'build/libs/*.jar'
          }
        )
      }
    }
  }
  triggers {
    pollSCM('* * * * *')
  }
}