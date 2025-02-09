package com.example.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KafkaTopicConfigTest {

    @Test
    void testWikiStreamsTopic() {
        KafkaTopicConfig config = new KafkaTopicConfig();
        NewTopic topic = config.wikiStreamsTopic();

        assertNotNull(topic, "Topic should not be null");
        assertEquals("WikiStreams", topic.name(), "Topic name should be WikiStreams");
        assertEquals(10, topic.numPartitions(), "Number of partitions should be 10");
    }

    @Test
    void testProcessedWikiStreamsTopic() {
        KafkaTopicConfig config = new KafkaTopicConfig();
        NewTopic topic = config.processedWikiStreamsTopic();

        assertNotNull(topic, "Topic should not be null");
        assertEquals("ProcessedWikiStreams", topic.name(), "Topic name should be ProcessedWikiStreams");
        assertEquals(10, topic.numPartitions(), "Number of partitions should be 10");
    }
}
