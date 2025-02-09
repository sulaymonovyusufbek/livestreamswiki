package com.example.wikipedia;

import com.example.Event.DiscordBotCommandListener;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.repository")
@EntityScan(basePackages = "com.example.Entity")
@ComponentScan(basePackages = {"com.example.service", "com.example.Entity", "com.example.Event", "com.example.repository","com.example.kafka","com.example.config"})  // Scan for other components
public class WikipediaApplication implements CommandLineRunner {

	@Value("${discord.bot.token}")
	private String botToken;

	@Autowired
	private final DiscordBotCommandListener discordBotCommandListener;



	public WikipediaApplication(DiscordBotCommandListener discordBotCommandListener) {
		this.discordBotCommandListener = discordBotCommandListener;
	}

	public static void main(String[] args) {
		SpringApplication.run(WikipediaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (botToken == null || botToken.isEmpty()) {
			System.err.println("Bot token is missing!");
			return;
		}

		try {
		       JDABuilder.createDefault(botToken)
					.addEventListeners(discordBotCommandListener)
					.build();
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains("LoginException")) {
				System.err.println("Login failed! Invalid token or credentials.");
			} else {
				System.err.println("An error occurred: " + e.getMessage());
			}
			e.printStackTrace();
		}
	}
}
