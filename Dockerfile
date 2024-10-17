FROM maven:3.9.8-eclipse-temurin-21 as builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

WORKDIR /usr/src/app

RUN mvn clean package

FROM openjdk:21
COPY --from=builder /usr/src/app/target/Luxmed-0.0.1-SNAPSHOT.jar /usr/app/Luxmed-0.0.1-SNAPSHOT.jar
WORKDIR /usr/app

ENTRYPOINT ["java","-jar","Luxmed-0.0.1-SNAPSHOT.jar"]
