# Kafka producer-consumer demo
This demo shows the most basic usage of Kafka's producer, consumer, streams, and admin APIs.

## Pre-requisites
Before running the demo, you need to install and start Kafka and Zookeeper.

Console 1:
```shell script
$ brew install zookeeper
$ brew install kafka
$ zkServer start
$ kafka-server-start /usr/local/etc/kafka/server.properties
```
To enable the topics delete option, add the setting `delete.topic.enable = true` to `server.properties`

Console 2:
```shell script
$ kafka-topics --create --topic test_topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```
or run `com.kafka_test.admin.TestAdmin`

## Docker 

```Create Docker Image
cd ./admin
docker build -t [image tag, e.g. kafka_test_admin] .
```
```Run Container
docker run [image tag, e.g. kafka_test_admin]
```

[10 best practices to build a Java container with Docker](https://snyk.io/blog/best-practices-to-build-java-containers-with-docker/)

## Local Sandbox with Docker

```Create And Run All Containers
docker-compose -f sandbox.yml up -d
```

* Zookeeper
* Kafka
* Admin Service
* Consumer Service
* Producer Service

