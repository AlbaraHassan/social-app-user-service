FROM maven:3.8.5-openjdk-17 as build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/userService-0.0.1-SNAPSHOT.jar userService.jar
EXPOSE 8083

ENTRYPOINT ["java","-jar","userService.jar"]
