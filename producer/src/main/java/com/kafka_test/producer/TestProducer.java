package com.kafka_test.producer;

import java.util.Properties;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

@SuppressWarnings("all")
public class TestProducer {
    private static final String TOPIC_NAME = "test_topic";
    private static Logger logger = Logger.getLogger(TestProducer.class.getName());

    public static void main(String[] args) {
        logger.info(String.format("Bootstrap Server Host: %s", args[0]));
        try (Producer<String, String> producer = new KafkaProducer<>(getProducerProperties(args[0]))) {
            logger.info("Producer started");

            for (int recordNumber = 1; recordNumber <= 5; ++recordNumber) {
                logger.info("Sending record " + recordNumber);
                ProducerRecord<String, String> record =
                        new ProducerRecord<>(TOPIC_NAME, Integer.toString(recordNumber), "Message " + recordNumber);
                producer.send(record).get();
            }

            logger.info("All records sent");
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
    }

    private static Properties getProducerProperties(String bootstrapServerUri) {
        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerUri);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return properties;
    }
}