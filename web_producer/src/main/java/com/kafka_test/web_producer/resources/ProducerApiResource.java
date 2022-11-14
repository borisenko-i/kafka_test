package com.kafka_test.web_producer.resources;

import com.kafka_test.web_producer.Main;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/resource")
@SuppressWarnings("all")
public class ProducerApiResource {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static final String KAFKA_BOOTSTRAP_HOST_KEY = "KAFKA_BOOTSTRAP_HOST";

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    public String getBook(@PathVariable int id) {
        return String.format("Resource #%d", id);
    }

    /*@Context
    private Configuration resourceConfig;

    @PutMapping(consumes = "application/json")
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
    } */
}