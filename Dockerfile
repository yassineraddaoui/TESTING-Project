FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ./book-network/target/book-network-api-0.0.1-SNAPSHOT.jar /app/book-network-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/book-network-api.jar"]