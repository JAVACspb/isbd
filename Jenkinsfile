pipeline {
    agent any
    environment {
        GRADLE_TOOL = tool name: 'Default', type: 'hudson.plugins.gradle.GradleInstallation'
        GRADLE = "${GRADLE_TOOL}\\bin\\gradle.bat"
        JAR_PATH = "app-boot-service/build/libs"
        JAR_NAME = "app-boot-service-1.0.0.jar"
        JAR = "${JAR_PATH}/${JAR_NAME}"
        DB = credentials('48653492-daa4-4362-b414-9ba63e8826b2')
    }
    parameters {
        string(name: 'profile', defaultValue: 'local', description: 'profile of deploy')
    }
    stages {
        stage('Build') {
            steps {
                withGradle {
                    bat "${GRADLE} build"
                    archiveArtifacts artifacts: "${JAR}", fingerprint: true
                }
            }
        }
        stage('Deploy') {
            steps {
                bat "java -jar -Dspring.profiles.active=${params.profile} -Dspring.datasource.jdbcUrl=jdbc:postgresql://localhost:5432/postgres -Dspring.datasource.username=${DB_USR} -Dspring.datasource.password=${DB_PSW} ${JAR}"
            }
        }
    }
}