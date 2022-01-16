FROM gradle:7.3.3-jdk11-alpine@sha256:7f63364a3033566df98285342ca56a3e0494d10ffcd13c6d4e9594e6b7f97b5c AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11.0.4-jre-slim@sha256:c967fe34180ad9182a7bb52d7b57a807a2a5e07242b452388d48ab5d7a62cd8a
RUN mkdir /app
COPY --from=build /home/gradle/src/build/distributions/producer-1.0-SNAPSHOT.tar.gz /app
WORKDIR /app/producer-1.0-SNAPSHOT
CMD "./bin/producer" "host.docker.internal"