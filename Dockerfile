FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/message-router.jar /app/app.jar

ENTRYPOINT ["java", "--enable-preview", "-jar", "/app/app.jar"]