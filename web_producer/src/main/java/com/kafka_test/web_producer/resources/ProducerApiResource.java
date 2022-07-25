package com.kafka_test.web_producer.resources;

import com.kafka_test.web_producer.Main;
import com.kafka_test.web_producer.KafkaProducerWrapper;
import com.kafka_test.web_producer.exceptions.ProcessRequestException;
import com.kafka_test.web_producer.exceptions.BadProduceRequestException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;
import java.util.logging.Logger;

@Path("/")
@SuppressWarnings("all")
public class ProducerApiResource {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static final String KAFKA_BOOTSTRAP_HOST_KEY = "KAFKA_BOOTSTRAP_HOST";

    @Context
    private Configuration resourceConfig;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(ProduceRequest request) {
        LOGGER.info("Create request received");

        validateRequest(request);

        Map<String, Object> properties = resourceConfig.getProperties();
        String kafkaBootstrapHost = (String) properties.get(KAFKA_BOOTSTRAP_HOST_KEY);

        KafkaProducerWrapper producer = new KafkaProducerWrapper(kafkaBootstrapHost);
        try {
            producer.sendRecords(request.requestId, request.numRecords);
        } catch (Exception e) {
            throw new ProcessRequestException(request.requestId);
        }
    }

    private static void validateRequest(ProduceRequest request) {
        if (request == null) {
            throw new BadProduceRequestException("Request body can't be empty.");
        }
        if (request.numRecords < 1) {
            throw new BadProduceRequestException("'numRecords' can't be less than 1.");
        }
        if (request.requestId.isEmpty()) {
            throw new BadProduceRequestException("'requestId' can't be empty.");
        }
    }
}