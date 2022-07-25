package com.kafka_test.web_producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class KafkaProducerWrapper {
    private static final String TOPIC_NAME = "test_topic";
    private static final Logger LOGGER = Logger.getLogger(KafkaProducerWrapper.class.getName());

    private Producer<String, String> producer;

    public KafkaProducerWrapper(String bootstrapServerHost) {
        LOGGER.info(String.format("Bootstrap Server Host: %s", bootstrapServerHost));
        producer = new KafkaProducer<>(getProducerProperties(bootstrapServerHost));
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

    public void sendRecords(String requestId, int numRecords) throws ExecutionException, InterruptedException {
        try {
            LOGGER.info("Producer started");

            for (int recordNumber = 1; recordNumber <= numRecords; ++recordNumber) {
                LOGGER.info("Sending record " + recordNumber);
                String message = String.format("Request ID: %s, message: %d", requestId, recordNumber);
                ProducerRecord<String, String> producerRecord =
                        new ProducerRecord<>(TOPIC_NAME, Integer.toString(recordNumber), message);
                producer.send(producerRecord).get();
            }

            LOGGER.info("All records sent");
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
            throw ex;
        }
    }
}
