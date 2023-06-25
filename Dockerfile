FROM openjdk:17-jdk-alpine

# Copy the JAR file into the container
COPY target/*.jar bookstore.jar

# Specify the command to run when the container starts
ENTRYPOINT ["java", "-jar", "/bookstore.jar"]