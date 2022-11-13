package com.kafka_test.producer;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@ComponentScan("com.kafka_test.producer")
@Configuration
public class ProducerServiceTestConfiguration {

    @Bean
    @Primary
    public RecordSender recordSender() {
        return Mockito.mock(RecordSender.class);
    }
}
