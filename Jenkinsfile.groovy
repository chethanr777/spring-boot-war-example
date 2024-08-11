node {
    def TOMCAT_USER = 'ubuntu'
    def TOMCAT_HOST = '18.60.93.17'
    def TOMCAT_PATH = '/home/ubuntu/tomcat/webapps/'

    stage('Checkout') {
        git branch: 'master', credentialsId: 'github', url: 'https://github.com/chethanr777/spring-boot-war-example.git'
    }

    stage('Build') {
        try {
            sh 'mvn clean package'
            archiveArtifacts artifacts: '**/target/*.war'
            echo "Archiving the Artifacts"
        } catch (Exception e) {
            error "Build failed: ${e.message}"
        }
    }

    stage('Deploy') {
        try {
            sshagent(['Master-tomcat']) {
                sh "rsync -avz -e 'ssh -o StrictHostKeyChecking=no' --delete target/*.war ${TOMCAT_USER}@${TOMCAT_HOST}:${TOMCAT_PATH}"
            }
            echo "Deployment succeeded!"
        } catch (Exception e) {
            echo "Deployment failed: ${e.message}"
            error "Deployment failed!"
        }
    }
}
