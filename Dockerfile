FROM openjdk:17-oracle
WORKDIR /app
ARG JAR_FILE=app/email-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app/app.jar
ENTRYPOINT ["java", "-jar", "app/app.jar"]