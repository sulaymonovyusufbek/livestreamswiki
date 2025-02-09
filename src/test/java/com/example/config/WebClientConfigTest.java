package com.example.config;

import io.netty.channel.ChannelOption;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import static org.junit.jupiter.api.Assertions.*;

class WebClientConfigTest {

    @Test
    void testWebClientBuilder() {
        WebClientConfig config = new WebClientConfig();
        WebClient.Builder builder = config.webClientBuilder();

        assertNotNull(builder, "WebClient.Builder should not be null");

        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(tcpClient ->
                        tcpClient.option(ChannelOption.SO_RCVBUF, 1024 * 1024)
                                .option(ChannelOption.SO_RCVBUF, 4 * 1024 * 1024)
                );

        WebClient.Builder expectedBuilder = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));

        assertNotNull(expectedBuilder, "Expected WebClient.Builder should not be null");
    }
}
