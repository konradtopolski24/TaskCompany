FROM openjdk:21-slim
COPY target/task-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]