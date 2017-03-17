pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh './gradlew test'
      }
    }
    stage('Publish test results') {
      steps {
        parallel(
          "Publish test results": {
            junit 'build/test-results/**/*.xml'
            
          },
          "Build javadoc": {
            sh './gradlew javadocJar'
            
          },
          "Build sources": {
            sh './gradlew sourcesJar'
            
          },
          "Build lib": {
            sh './gradlew jar'
            
          }
        )
      }
    }
  }
  triggers {
    pollSCM('* * * * *')
  }
}