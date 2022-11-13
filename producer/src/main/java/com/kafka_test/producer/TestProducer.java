package com.kafka_test.producer;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

@SuppressWarnings("all")
public class TestProducer {
    private static Logger logger = Logger.getLogger(TestProducer.class.getName());

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Configuration.class);
        AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();

        ProducerService service = factory.getBean(ProducerService.class);
        service.run(args[0]);
    }
}