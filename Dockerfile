FROM openjdk:17-jdk-alpine
ADD target/bookstore-0.0.1-SNAPSHOT.jar bookstore.jar
ENTRYPOINT ["java", "-jar","bookstore.jar"]