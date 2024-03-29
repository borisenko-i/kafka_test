package com.kafka_test.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

@SuppressWarnings("all")
public class TestConsumer {
    private static final String TOPIC_NAME = "test_topic";
    private static final String OUTPUT_PATH = "output.txt";

    private static Logger logger = Logger.getLogger(TestConsumer.class.getName());

    public static void main(String[] args) {
        logger.info(String.format("Bootstrap Server Host: %s", args[0]));

        try (Consumer<String, String> consumer = new KafkaConsumer<>(getConsumerProperties(args[0]))) {
            logger.info("Consumer started");

            // To be able to seek within a specific partition, we need to assign the consumer to that parition
            // instead of subscribing to a topic. When subscribing to a topic, the partitions are being assigned
            // automatically, and `seek` won't work.
            consumer.assign(Collections.singletonList(new TopicPartition(TOPIC_NAME, 0)));
            consumer.seek(new TopicPartition(TOPIC_NAME, 0), 0);
            // consumer.subscribe(Collections.singletonList(TOPIC_NAME));

            while (true) {
                ConsumerRecords<String, String> newRecords = consumer.poll(Duration.ofMillis(100));
                newRecords.forEach(TestConsumer::processRecord);
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    private static Properties getConsumerProperties(String bootstrapServerUri) {
        Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerUri);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-payments");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return properties;
    }

    private static void processRecord(ConsumerRecord<String, String> record) {
        String outputString =
                String.format(
                        "Offset: %d, key: %s, value: %s",
                        record.offset(),
                        record.key(),
                        record.value());
        logger.info(outputString);
        writeToFile(outputString);
    }

    private static void writeToFile(String string) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH));
            writer.write(string);
            writer.close();
        } catch (Exception e) {
            logger.severe("Error when writing to file: " + e.getMessage());
        }
    }
}