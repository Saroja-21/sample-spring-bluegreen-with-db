FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 80

ENV SERVER_PORT=80

ENTRYPOINT ["java", "-jar", "app.jar"]
