FROM openjdk:17-jdk-alpine

ARG JWT_SECRET_KEY
ENV JWT_SECRET_KEY=$JWT_SECRET_KEY

# Copy the JAR file into the container
COPY target/bookstore-0.0.1-SNAPSHOT.jar bookstore.jar

# Specify the command to run when the container starts
ENTRYPOINT ["java", "-jar", "/bookstore.jar"]