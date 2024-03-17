pipeline{
    agent any
    tools {
        maven "MAVEN"
        
    }
    environment{
        AWS_DEFAULT_REGION = "us-east-1"
        ECR_REPO_NAME = "637423560039.dkr.ecr.us-east-1.amazonaws.com/nagesh7742"
        AWS_ACCOUNT_ID = "637423560039"
        
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
        stage("image push to ECR"){
            steps{
                script{
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'your_aws_credentials_id', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                    sh "aws ecr get-login-password --region $AWS_DEFAULT_REGION | ${dockerPath} login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com"
                
                    
                    }
                
                }
            }
        }
        
    }
}
