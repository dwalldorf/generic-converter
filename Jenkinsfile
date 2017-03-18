pipeline {
  agent docker
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
                "Archive test results": {
                  junit 'build/test-results/**/*.xml'

                  archiveArtifacts 'build/test-results/test/*.xml'
                  fingerprint 'build/test-results/test/*.xml'
                },
                "Archive libs": {
                  archiveArtifacts 'build/libs/*.jar'
                  fingerprint 'build/libs/*.jar'
                },
                "Archive pom": {
                  archiveArtifacts 'build/pom.xml'
                  fingerprint 'build/pom.xml'
                },
                "Archive Jenkinsfile": {
                  archiveArtifacts 'Jenkinsfile'
                  fingerprint 'Jenkinsfile'
                }
        )
      }
    }
    stage('Publish Artifact') {
      when {
        expression {
          GIT_BRANCH = 'origin/' + sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
          return GIT_BRANCH == 'origin/master' || params.FORCE_FULL_BUILD
        }
      }
      steps {
        withCredentials([[$class          : 'UsernamePasswordMultiBinding', credentialsId: 'bintray',
                          usernameVariable: 'BINTRAY_USERNAME', passwordVariable: 'BINTRAY_API_KEY']]) {
          sh './gradlew bintrayUpload'
        }
      }
    }
  }
  triggers {
    pollSCM('* * * * *')
  }
}