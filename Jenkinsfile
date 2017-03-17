pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        libraryResource 'jadecr-secret'
        sh './gradlew test'
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
