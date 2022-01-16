package com.kafka_test.admin;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@SuppressWarnings("all")
public class TestAdmin {
    private static final String TOPIC_NAME = "test_topic";
    private static Logger logger = Logger.getLogger(TestAdmin.class.getName());

    public static void main(String[] args) {
        try (AdminClient adminClient = AdminClient.create(getAdminProperties())) {
            if (Arrays.asList(args).contains("--delete")) {
                logger.info("Deleting topic ...");
                deleteTopic(adminClient);
            } else {
                logger.info("Creating topic ...");
                Set<String> existingTopics = listTopics(adminClient);
                logger.info(String.format("Existing Topics: \n%s", String.join("\n", existingTopics)));

                if (existingTopics.contains(TOPIC_NAME)) {
                    logger.info(String.format("Topic '%s' already exists", TOPIC_NAME));
                } else {
                    createTopic(adminClient);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.severe(e.getMessage());
        }
    }

    private static void deleteTopic(Admin adminClient) throws InterruptedException, ExecutionException {
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Collections.singletonList(TOPIC_NAME));
        deleteTopicsResult.topicNameValues().get(TOPIC_NAME).get();
        logger.info(String.format("Existing topic %s was deleted.", TOPIC_NAME));
    }

    private static void createTopic(Admin adminClient) throws InterruptedException, ExecutionException {
        int numPartitions = 1;
        short replicationFactor = 1;
        NewTopic newTopic = new NewTopic(TOPIC_NAME, Optional.of(numPartitions), Optional.of(replicationFactor));
        CreateTopicsResult createTopicsResult = adminClient.createTopics(Collections.singletonList(newTopic));
        createTopicsResult.all().get();

        DescribeTopicsOptions options = new DescribeTopicsOptions();
        options.timeoutMs(1000);
        DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Collections.singletonList(TOPIC_NAME), options);
        TopicDescription topicDescription = describeTopicsResult.values().get(TOPIC_NAME).get();

        logger.info(String.format("Topic '%s' is created. Topic Id: %s", TOPIC_NAME, topicDescription.topicId()));
    }

    private static Set<String> listTopics(Admin adminClient) throws ExecutionException, InterruptedException {
        ListTopicsOptions options = new ListTopicsOptions();
        options.timeoutMs(1000);
        options.listInternal(true);
        ListTopicsResult topics = adminClient.listTopics(options);
        return topics.names().get();
    }

    private static Properties getAdminProperties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return properties;
    }
}
