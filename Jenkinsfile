pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh './gradlew test'
        libraryResource 'jadecr-secret'
      }
    }
  }
  tools {
    gradle 'gradle3'
  }
  triggers {
    pollSCM('* * * * *')
  }
}