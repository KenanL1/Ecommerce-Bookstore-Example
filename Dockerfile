FROM openjdk:17-jdk-alpine

# Set the JAR_PATH build argument
ARG JAR_PATH

# Copy the JAR file into the container
COPY ${JAR_PATH} /bookstore.jar

# Specify the command to run when the container starts
ENTRYPOINT ["java", "-jar", "/bookstore.jar"]