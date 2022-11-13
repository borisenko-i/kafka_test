package com.kafka_test.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes =  ProducerServiceTestConfiguration.class)
public class ProducerServiceSpec {
    @Autowired
    private ProducerService producerService;

    @Autowired
    private RecordSender sender;

    @Test
    public void test() {
        producerService.run("localhost:29093");

        verify(sender).setServerAddress(any());
        verify(sender).send(5);
        verifyNoMoreInteractions(sender);
    }
}
