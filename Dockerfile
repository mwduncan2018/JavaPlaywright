# Use the official Microsoft Playwright image with Java
FROM mcr.microsoft.com/playwright/java:v1.40.0-jammy

# Set the working directory in the container
WORKDIR /app

# Copy your pom.xml and source code
COPY pom.xml .
COPY src ./src

# Install Maven (the Playwright image is Ubuntu-based)
RUN apt-get update && apt-get install -y maven

# Run maven to download dependencies (helps with caching)
RUN mvn dependency:go-offline

# Command to run your tests
# We use 'allure-results' so we can pull them out later
CMD ["mvn", "test", "-Dcucumber.options=--plugin io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"]