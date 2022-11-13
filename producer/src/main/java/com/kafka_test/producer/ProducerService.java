package com.kafka_test.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@SuppressWarnings("all")
public class ProducerService {
    private static final Logger logger = Logger.getLogger(ProducerService.class.getName());

    @Autowired
    RecordSender recordSender;

    public void run(String serverAddress) {
        logger.info(String.format("Bootstrap Server Host: %s", serverAddress));

        logger.info("Starting to send ...");
        recordSender.setServerAddress(serverAddress);
        recordSender.send(5);

        logger.info("Done");
    }
}
