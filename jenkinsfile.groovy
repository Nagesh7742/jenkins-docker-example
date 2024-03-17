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
                    sh "${dockerPath} build -t nagesh7742/nagesh:v1.0.0 ."
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
                    withCredentials([string(credentialsId: 'nagesh7742', variable: 'dockerhubpwd')]) {
                    def dockerPath = "/usr/local/bin/docker"

                    sh "${dockerPath} login -u nagesh7742 -p ${dockerhubpwd}"
                    
                    }
                    def dockerPath = "/usr/local/bin/docker"
                    
                    sh "${dockerPath} push nagesh7742/nagesh:v1.0.0"
                }
            }
        }
    }
}
