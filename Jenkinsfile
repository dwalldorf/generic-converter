pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'gradle test'
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
