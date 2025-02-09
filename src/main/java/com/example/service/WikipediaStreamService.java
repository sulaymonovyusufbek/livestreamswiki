package com.example.service;

import com.example.DTO.RecentChange;
import com.example.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
@Service
public class WikipediaStreamService {

    private final WebClient webClient;
    private final DailyStatsService dailyStatsService;
    private final KafkaProducer kafkaProducer;


    @Value("${wikipedia.stream.url}")
    private String streamUrl;

    public WikipediaStreamService(WebClient.Builder webClientBuilder, DailyStatsService dailyStatsService, KafkaProducer kafkaProducer) {
        this.webClient = webClientBuilder.baseUrl(streamUrl).build();
        this.dailyStatsService = dailyStatsService;
        this.kafkaProducer = kafkaProducer;
    }

    public Flux<RecentChange> listenToRecentChanges() {
        return webClient.get()
                .uri(streamUrl)
                .retrieve()
                .bodyToFlux(RecentChange.class)
                .onErrorResume(e -> Flux.empty())
                .doOnNext(change -> {
                    String language = change.getWiki();
                    dailyStatsService.incrementChangeCount(language);
                    kafkaProducer.sendMessage("WikiStreams", change.toString());
                });
}

}

