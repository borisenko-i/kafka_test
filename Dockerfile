FROM gradle:6.9-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app
ENTRYPOINT ["java", "-jar", "/app/kafka_test-1.0-SNAPSHOT.jar"]
