FROM maven:3.9.6-eclipse-temurin-21 as builder
WORKDIR /usr/app
COPY . /usr/app/.
RUN mvn -f /usr/app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:23-ea-17-jdk-slim
WORKDIR /usr/src/app
COPY --from=builder usr/app/target/*.jar /usr/src/app/app.jar
CMD ["java", "-jar", "app.jar"]

