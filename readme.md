# Kafka + Docker demo
This demo shows the most basic usage of Kafka's producer, consumer, streams, and admin APIs. Besides that, it contains Dockerfiles to run the apps in containers, and a docker-compose file to run a local sandbox with a single command.

The project is confirmed to build with Gradle v7.4.1, there might be build errors with earlier versions.

## Running on localhost
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

## Local Sandbox with Docker Compose

```Create And Run All Containers
docker compose -f sandbox.yml up -d
```

* Zookeeper
* Kafka
* Admin Service
* Consumer Service
* Producer Service

## Kubernetes

The k8s configs are located in the `kubernetes` subdirectory.

To run the project locally on `minikube`, do the following:
```
$ minikube start
$ kubectl apply -f kubernetes/
```

To open the dashboard, run:
```
$ minikube dashboard
```

In case of minikube start-up errors, try:
```
$ minikube delete
```
