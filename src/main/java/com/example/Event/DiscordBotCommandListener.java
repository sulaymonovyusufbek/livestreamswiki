package com.example.Event;

import com.example.service.DailyStatsService;
import com.example.service.LanguageService;
import com.example.service.WikipediaStreamService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDate;

@Component
public class DiscordBotCommandListener extends ListenerAdapter {

    @Autowired
    private LanguageService languageService;
    @Autowired
    private WikipediaStreamService wikipediaStreamService;
    @Autowired
    private DailyStatsService dailyStatsService;

    private Disposable currentStreamSubscription = null;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();

        if (content.startsWith("!setLang")) {
            handleSetLang(event, content);
        } else if (content.startsWith("!recent")) {
            handleRecentChanges(event);
        } else if (content.startsWith("!stop")) {
            handleStopStream(event);
        } else if (content.startsWith("!stats")) {
            handleStats(event, content);
        }
    }

    private void handleSetLang(MessageReceivedEvent event, String content) {
        String[] parts = content.split(" ");
        if (parts.length == 2) {
            String languageCode = parts[1];
            languageService.setLanguage(event.getAuthor().getIdLong(), languageCode);
            event.getChannel().sendMessage("Language set to " + languageCode).queue();
        } else {
            event.getChannel().sendMessage("Usage: `!setLang <language_code>`").queue();
        }
    }

    private void handleRecentChanges(MessageReceivedEvent event) {
        String language = languageService.getLanguage(event.getAuthor().getIdLong());

        if (currentStreamSubscription != null && !currentStreamSubscription.isDisposed()) {
            event.getChannel().sendMessage("Stream is already active! Use `!stop` to stop it first.").queue();
            return;
        }

        Flux<String> changes = wikipediaStreamService.listenToRecentChanges()
                .filter(change -> change.getWiki().startsWith(language))
                .map(change -> "**Title:** " + change.getTitle() + "\n" +
                        "**User:** " + change.getUser() + "\n" +
                        "**URL:** " + change.getTitleUrl() + "\n" +
                        "**Timestamp:** " + change.getFormattedTimestamp())
                .onErrorReturn("Error fetching Wikipedia changes!")
                .delayElements(Duration.ofSeconds(3));


        currentStreamSubscription = changes.subscribe(message -> {
            if (message.length() > 2000) {
                sendLargeMessage(event, message);
            } else {
                event.getChannel().sendMessage(message).queue();
            }
        });
        event.getChannel().sendMessage("Stream started!").queue();
    }


    private void handleStopStream(MessageReceivedEvent event) {
        if (currentStreamSubscription != null && !currentStreamSubscription.isDisposed()) {
            currentStreamSubscription.dispose();
            currentStreamSubscription = null;
            event.getChannel().sendMessage("Stream stopped.").queue();
        } else {
            event.getChannel().sendMessage("No active stream to stop.").queue();
        }
    }

    private void handleStats(MessageReceivedEvent event, String content) {
        String[] parts = content.split(" ");
        if (parts.length == 2) {
            try {
                LocalDate date = LocalDate.parse(parts[1]);
                String language = languageService.getLanguage(event.getAuthor().getIdLong());
                int changeCount = dailyStatsService.getChangeCountForDate(language, date);

                System.out.println("Fetching stats for language: " + language + " on date: " + date);
                event.getChannel().sendMessage("📊 Changes for **" + language + "** on **" + date + "**: **" + changeCount + "**").queue();
            } catch (Exception e) {
                event.getChannel().sendMessage("Invalid date format. Use `yyyy-MM-dd`").queue();
            }
        } else {
            event.getChannel().sendMessage("Usage: `!stats yyyy-MM-dd`").queue();
        }
    }

    private void sendLargeMessage(MessageReceivedEvent event, String message) {
        int chunkSize = 2000;
        int length = message.length();
        for (int i = 0; i < length; i += chunkSize) {
            String chunk = message.substring(i, Math.min(i + chunkSize, length));
            event.getChannel().sendMessage(chunk).queue();
        }
    }
}
