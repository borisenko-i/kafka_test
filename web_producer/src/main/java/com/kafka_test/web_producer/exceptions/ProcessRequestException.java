package com.kafka_test.web_producer.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ProcessRequestException extends WebApplicationException {
    public ProcessRequestException(String requestId) {
        super(
                Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .entity(String.format("Server failed to process request %s", requestId))
                        .type(MediaType.TEXT_PLAIN)
                        .build());
    }
}
