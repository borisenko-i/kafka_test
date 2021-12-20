# Kafka producer-consumer demo
This demo shows the most basic implementation of Kafka producer and consumer.

## Pre-requisites
Before running the demo, you need to install and start Kafka and Zookeeper, and create a new topic.

Console 1:
```shell script
$ brew install zookeeper
$ brew install kafka
$ zkServer start
$ kafka-server-start /usr/local/etc/kafka/server.properties
```
To enable topics delete option the setting `delete.topic.enable = true` should be added to the `server.properties`

Console 2:
```shell script
$ kafka-topics --create --topic test_topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```
or run `com.kafka_test.admin.TestAdmin`