# -------- Stage 1: Artifact Stage --------
FROM eclipse-temurin:21-jre-alpine AS artifact

WORKDIR /app

# Copy pre-built jar from CI
COPY target/*.jar app.jar


# -------- Stage 2: Runtime Stage --------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy jar from artifact stage
COPY --from=artifact /app/app.jar app.jar

EXPOSE 80

ENV SERVER_PORT=80

ENTRYPOINT ["java", "-jar", "app.jar"]
