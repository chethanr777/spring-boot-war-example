pipeline {
            agent any
  /*tools {
          maven 'maven'
        } */
environment {
          TOMCAT_USER = 'ubuntu'
          TOMCAT_HOST = 'http://18.61.162.108'
          TOMCAT_PATH = '/home/ubuntu/tomcat/webapps'
}
stages {
          stage('Checkout') {
            steps {
                  git branch: 'master', credentialsId: 'github', url: 'https://github.com/chethanr777/spring-boot-war-example.git'
                  }
                              }
          stage('Build') {
              steps {
                  sh 'mvn clean package'

                      }
              post {
          success {
                      echo "Archiving the Artifacts"
                      archiveArtifacts artifacts: '**/target/*.war'
                    }
                    }
                            }
          stage('Deploy') {
        steps {
        sshagent(['ubuntu']) {
      sh "rsync -avz -e 'ssh -o StrictHostKeyChecking=no' --delete target/*.war ${TOMCAT_USER}@${TOMCAT_HOST}:${TOMCAT_PATH}"
                            }
            }
                          }
            }
    }
