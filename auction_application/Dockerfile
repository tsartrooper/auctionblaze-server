FROM eclipse-temurin:17-jdk-alpine as build

WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .


RUN ./mvnw dependency:go-offline

COPY src src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]