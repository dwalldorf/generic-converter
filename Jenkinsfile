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
            sh './gradlew javadoc'
            
          },
          "Build sources": {
            sh './gradlew sourcesJar'
            
          },
          "Build lib": {
            sh './gradlew build'
            
          }
        )
      }
    }
  }
  triggers {
    pollSCM('* * * * *')
  }
}