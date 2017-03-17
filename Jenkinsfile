pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'mvn clean verify'
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
