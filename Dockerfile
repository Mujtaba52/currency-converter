# First stage: Build the Spring Boot app with Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Run Maven to build the Spring Boot application
RUN mvn clean package -DskipTests

# Second stage: Create a lightweight runtime container
FROM openjdk:17-jdk-slim

# Set working directory for runtime
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
