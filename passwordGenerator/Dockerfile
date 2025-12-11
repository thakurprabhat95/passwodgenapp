# -----------------------------------------------------------------------------
# 1. BUILD STAGE: Compiles the Spring Boot application into a single JAR file
# -----------------------------------------------------------------------------
    FROM openjdk:17-jdk-slim AS build
    # Set working directory inside the container
    WORKDIR /app
    
    # Copy Maven wrapper files and pom.xml first to take advantage of Docker caching.
    # This step only runs again if pom.xml or wrapper files change.
    COPY mvnw .
    COPY .mvn .mvn
    COPY pom.xml .
    
    # Download dependencies (this is a fast layer due to caching)
    # This prevents downloading all dependencies every time you change a source file.
    RUN ./mvnw dependency:go-offline
    
    # Copy the rest of the application source code
    # This includes all Java files and the static frontend files (index.html, app.js, style.css)
    # which are expected to be in src/main/resources/static or similar.
    COPY src src
    
    # Package the application. -DskipTests speeds up the build.
    RUN ./mvnw package -DskipTests
    
    # -----------------------------------------------------------------------------
    # 2. RUN STAGE: Creates a small, secure image to run the final JAR
    # -----------------------------------------------------------------------------
    # Use a JRE-only image for the final product—it's much smaller and more secure.
    FROM openjdk:17-jre-slim
    WORKDIR /app
    
    # Spring Boot defaults to port 8080. It's good practice to expose it.
    EXPOSE 8080
    
    # Define the location of the JAR file from the build stage.
    # Based on your pom.xml (artifactId: passwordGenerator, version: 0.0.1-SNAPSHOT), 
    # the resulting file is: target/passwordGenerator-0.0.1-SNAPSHOT.jar
    # We use a wildcard (*) to ensure it works even if the snapshot version number changes.
    ARG JAR_FILE=/app/target/*.jar
    
    # Copy the built JAR from the 'build' stage to the final image and rename it to 'app.jar'
    COPY --from=build ${JAR_FILE} app.jar
    
    # Command to run the Spring Boot application
    ENTRYPOINT ["java", "-jar", "app.jar"]
