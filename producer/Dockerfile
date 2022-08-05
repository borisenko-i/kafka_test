FROM openjdk:11.0.4-jre-slim@sha256:c967fe34180ad9182a7bb52d7b57a807a2a5e07242b452388d48ab5d7a62cd8a
RUN mkdir /app
ADD ./build/distributions/producer-1.0-SNAPSHOT.tar.gz /app
WORKDIR /app/producer-1.0-SNAPSHOT
CMD "./bin/producer" "kafka-service:29093"
