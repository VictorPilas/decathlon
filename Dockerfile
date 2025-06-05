FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY target/decathlon-0.0.1-SNAPSHOT.jar decathlon-service.jar

ENTRYPOINT ["java","-jar","/decathlon-service.jar"]