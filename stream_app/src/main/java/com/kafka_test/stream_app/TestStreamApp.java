package com.kafka_test.stream_app;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.*;
import java.util.logging.Logger;

@SuppressWarnings("all")
public class TestStreamApp {
    private static final String INPUT_TOPIC = "test_topic";
    private static final String OUTPUT_TOPIC = "output_topic";
    private static Logger logger = Logger.getLogger(TestStreamApp.class.getName());

    public static void main(String[] args) {
        StreamsBuilder builder = new StreamsBuilder();
        builder
                .stream(INPUT_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
                .mapValues((ValueMapper<String, String>) String::toUpperCase)
                .to(OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.String()));

        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), getStreamProperties());
        kafkaStreams.start();
        logger.info("Stream App Started");
    }

    private static Properties getStreamProperties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-stream-app");
        return properties;
    }
}