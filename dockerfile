# Use the official OpenJDK 17 slim base image
FROM openjdk:17-jdk-slim

# Add metadata (optional but recommended for documentation)
LABEL maintainer="Your Name <youremail@example.com>" \
      description="Spring Boot application running on OpenJDK 17" \
      version="1.0"

# Install necessary tools and clean up afterwards to reduce image size
RUN apt-get update && \
    apt-get install -y --no-install-recommends curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/*.jar app.jar

# Expose the application port (make sure it matches your app's configuration)
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
