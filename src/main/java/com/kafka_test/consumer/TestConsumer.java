package com.kafka_test.consumer;

import com.kafka_test.producer.TestProducer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

@SuppressWarnings("all")
public class TestConsumer {
    private static final String TOPIC_NAME = "test_topic";
    private static Logger logger = Logger.getLogger(TestProducer.class.getName());

    public static void main(String[] args) {
        try (Consumer<String, String> consumer = new KafkaConsumer<>(getConsumerProperties())) {
            logger.info("Consumer started");

            consumer.subscribe(Collections.singletonList(TOPIC_NAME));

            while (true) {
                ConsumerRecords<String, String> newRecords = consumer.poll(Duration.ofMillis(100));
                newRecords.forEach(r -> logger.info(r.key() + ": " + r.value()));
            }
        }
    }

    private static Properties getConsumerProperties() {
        Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-payments");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return properties;
    }
}
