FROM openjdk:11.0.4-jre-slim@sha256:c967fe34180ad9182a7bb52d7b57a807a2a5e07242b452388d48ab5d7a62cd8a
RUN mkdir /app
ADD ./build/distributions/web_producer-1.0-SNAPSHOT.tar.gz /app
WORKDIR /app/web_producer-1.0-SNAPSHOT
CMD "./bin/web_producer" "$WEB_PRODUCER_HOST" "$WEB_PRODUCER_PORT" "$KAFKA_BOOTSTRAP_SERVER_ADDRESS"