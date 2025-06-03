# OpenJDK base image
FROM eclipse-temurin:17

#Copying built jar into image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

#Running the app
ENTRYPOINT ["java","-jar","/app.jar"]
