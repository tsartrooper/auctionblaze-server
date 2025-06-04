FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY auction_application/pom.xml .
RUN mvn -B dependency:go-offline

COPY auction_application/src ./src
RUN mvn -B package -DskipTests

FROM eclipse-temurin:17
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
