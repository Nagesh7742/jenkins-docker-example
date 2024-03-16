pipeline{
    agent any
    tools {
        maven "MAVEN"
        
    }
    stages{
        stage("maven build"){
            
            steps{
            
            checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: '3966a02c-4e10-4803-889b-3cd780e8d429', url: 'https://github.com/Nagesh7742/jenkins-docker-example.git']])
            sh "mvn clean install"
            
            }
        
            
        }
        stage("docker build"){
            steps{
                script{

                    def dockerPath = "/usr/local/bin/docker"
                    sh "${dockerPath} build -t nagesh:v1.0.0 ."
                }
            }
        }
        stage("docker run"){
            steps{
                script{
                    def dockerPath = "/usr/local/bin/docker"
                    sh "${dockerPath} run -d -p 8085:8080 nagesh:v1.0.0"
                }
            }
        }
        stage("docker push"){
            steps{
              script{
                
              }
            }
        }
      
    }
}
