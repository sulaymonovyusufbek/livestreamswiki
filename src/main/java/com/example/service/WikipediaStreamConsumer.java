package com.example.service;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WikipediaStreamConsumer {

    private final JDA jda;

    public WikipediaStreamConsumer(JDA jda) {
        this.jda = jda;
    }

    @KafkaListener(topics = "WikiStreams", groupId = "wikipedia-consumer-group")
    public void consumeProcessedEvent(String message) {
        try {

            System.out.println("Received message: " + message);
            sendToDiscord(message);
        } catch (Exception e) {
            System.err.println("Error processing message: " + message);
            e.printStackTrace();
        }
    }

    private void sendToDiscord(String message) {
        try {
            String channelId = "1337167870484025424";
            System.out.println("Attempting to send message to channel with ID: " + channelId);

            TextChannel channel = jda.getTextChannelById(channelId);

            if (channel != null) {
                System.out.println("Channel found, sending message...");
                channel.sendMessage(message).queue();
            } else {
                System.err.println("Discord channel not found.");
            }
        } catch (Exception e) {
            System.err.println("Error sending message to Discord: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
