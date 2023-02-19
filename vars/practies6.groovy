 // https://www.jenkins.io/doc/book/pipeline/shared-libraries/#defining-a-more-structured-dsl
 def call(Map params) {
    pipeline {
        agent any
        stages {
            stage ("Website") {
                steps {
                    echo "${params.domain}"
                }
            }
            stage ("Request Website") {
                steps {
                    script {
                        response_code = sh(
                            script: "curl -o /dev/null -s -w %{http_code} ${params.domain}",
                            returnStdout: true
                        ).toInteger()

                        echo "${response_code}"
                    }
                }
            }
            stage ("Check  Http Code") {
                steps {
                    script {
                        if( response_code != params.http_code) {
                            sh "false"
                        }
                    }
                    
                }
            }
        }
        post {
            success {
                echo "Success."
            }
            failure {
                withCredentials([string(credentialsId: 'telegram-bot', variable: 'TELEGRAM_TOKEN')]){
                    withCredentials([string(credentialsId: 'telegram-notification-group', variable: 'TELEGRAM_GROUP_ID')]) {
                        sh """#!/bin/bash
                            curl -X GET https://api.telegram.org/bot${TELEGRAM_TOKEN}/sendMessage -d "chat_id=${TELEGRAM_GROUP_ID}&text=\"\"${params.domain} response code != ${params.http_code}.\"\""
                        """
                    }
                }
            }
        }
    }
 }