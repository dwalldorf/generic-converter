pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
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
