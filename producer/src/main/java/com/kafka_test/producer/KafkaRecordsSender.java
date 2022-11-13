package com.kafka_test.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.logging.Logger;

@Component
@SuppressWarnings("all")
public class KafkaRecordsSender implements RecordSender {
    private static final String TOPIC_NAME = "test_topic";
    private static final Logger logger = Logger.getLogger(KafkaRecordsSender.class.getName());

    private String serverAddress = null;

    public void send(int number) {
        try (Producer<String, String> producer = new KafkaProducer<>(getProducerProperties())) {
            logger.info("Producer started");

            for (int recordNumber = 1; recordNumber <= number; ++recordNumber) {
                logger.info(String.format("Sending record %d", recordNumber));
                ProducerRecord<String, String> producerRecord =
                        new ProducerRecord<>(TOPIC_NAME, Integer.toString(recordNumber), "Message " + recordNumber);
                producer.send(producerRecord).get();
            }

            logger.info("All records sent");
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    private Properties getProducerProperties() {
        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddress);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return properties;
    }
}
