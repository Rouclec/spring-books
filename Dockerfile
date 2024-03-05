FROM openjdk:17-jdk-alpine
MAINTAINER rouclec.com
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]