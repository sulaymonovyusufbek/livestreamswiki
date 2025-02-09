package com.example.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

class KafkaProducerTest {

    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaProducer kafkaProducer;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        kafkaProducer = new KafkaProducer(kafkaTemplate);
    }

    @Test
    void testSendMessage_Success() {

        String topic = "test-topic";
        String message = "Hello Kafka!";
        CompletableFuture<SendResult<String, String>> future = CompletableFuture.completedFuture(mock(SendResult.class));
        when(kafkaTemplate.send(topic, message)).thenReturn(future);

        kafkaProducer.sendMessage(topic, message);

        verify(kafkaTemplate, times(1)).send(topic, message);
    }

    @Test
    void testSendMessage_Failure() {

        String topic = "test-topic";
        String message = "Hello Kafka!";
        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Kafka send failed"));
        when(kafkaTemplate.send(topic, message)).thenReturn(future);

        kafkaProducer.sendMessage(topic, message);

        verify(kafkaTemplate, times(1)).send(topic, message);
    }
}
