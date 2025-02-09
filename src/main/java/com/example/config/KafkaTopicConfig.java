package com.example.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public NewTopic wikiStreamsTopic(){
        return TopicBuilder.name("WikiStreams")
                .partitions(10)
                .build();
    }
    public NewTopic processedWikiStreamsTopic() {
        return TopicBuilder.name("ProcessedWikiStreams")
                .partitions(10)
                .build();
    }
}
