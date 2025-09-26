FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
RUN apk add --no-cache curl
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]