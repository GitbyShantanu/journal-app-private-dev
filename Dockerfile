# Use official Java runtime
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Expose port
EXPOSE 8081

# Run the jar
CMD ["java", "-jar", "target/journalApp-0.0.1-SNAPSHOT.jar"]
