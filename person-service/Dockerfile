# -------- Stage 1 (Artifact stage only) ----------
FROM scratch AS artifact

WORKDIR /artifact
COPY target/*.jar app.jar

# -------- Stage 2 (Runtime) ----------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=artifact /artifact/app.jar app.jar

EXPOSE 80
ENTRYPOINT ["java","-jar","app.jar"]
