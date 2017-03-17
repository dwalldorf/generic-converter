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
  triggers {
    pollSCM('* * * * *')
  }
}
