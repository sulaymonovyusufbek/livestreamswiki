package com.example.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JDAConfig {

    @Value("${discord.bot.token}")
    private String botToken;

    @Bean
    public JDA jda() {
        try {
            if (botToken == null || botToken.isEmpty()) {
                throw new RuntimeException("Discord bot token is missing in application.properties");
            }
            return JDABuilder.createDefault(botToken)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .build()
                    .awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to initialize JDA", e);
        }
    }
}
