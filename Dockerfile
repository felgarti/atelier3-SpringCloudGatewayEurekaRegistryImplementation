FROM openjdk:17

# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY ./out/artifacts/customer_service_jar/customer-service.jar /app

# Exposing port 8080
EXPOSE 8080

# Starting the application
CMD ["java", "-jar", "customer-service.jar"]