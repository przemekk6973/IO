FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/dziki-z-afryki-backend-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]