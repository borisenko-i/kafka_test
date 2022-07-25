package com.kafka_test.web_producer.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProduceRequest {
    public String requestId = "";
    public int numRecords = 0;
}
