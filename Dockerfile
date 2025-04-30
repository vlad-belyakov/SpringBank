# Базовый образ с Java
FROM openjdk:17-alpine

ARG JAR_FILE=target/SpringBank-1.0-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]