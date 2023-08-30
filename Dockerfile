FROM maven:3.8-jdk-11 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM amazoncorretto:11

WORKDIR /app

COPY --from=build /app/target/Mariosy-1.0-SNAPSHOT.jar /app/Mariosy.jar

EXPOSE 8081

CMD ["java", "-jar", "Mariosy.jar"]