FROM openjdk:17-jdk-alpine

# Copy the JAR file into the container
COPY --from=maven /home/runner/.m2/repository/ecommerce/bookstore/0.0.1-SNAPSHOT/bookstore-0.0.1-SNAPSHOT.jar bookstore.jar

# Specify the command to run when the container starts
ENTRYPOINT ["java", "-jar", "/bookstore.jar"]