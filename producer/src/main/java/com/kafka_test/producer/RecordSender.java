package com.kafka_test.producer;

import org.springframework.stereotype.Component;

public interface RecordSender {
    void send(int number);
    void setServerAddress(String properties);
}
