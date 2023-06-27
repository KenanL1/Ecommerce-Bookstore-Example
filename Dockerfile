FROM openjdk:17-jdk-alpine

ARG JWT_SECRET_KEY
ENV JWT_SECRET_KEY=$JWT_SECRET_KEY

ARG POSTGRES_DATABASE
ENV POSTGRES_DATABASE=$POSTGRES_DATABASE

ARG POSTGRES_USER
ENV POSTGRES_USER=$POSTGRES_USER

ARG POSTGRES_PASSWORD
ENV POSTGRES_PASSWORD=$POSTGRES_PASSWORD

# Copy the JAR file into the container
COPY target/bookstore-0.0.1-SNAPSHOT.jar bookstore.jar

# Specify the command to run when the container starts
ENTRYPOINT ["java", "-jar", "/bookstore.jar"]