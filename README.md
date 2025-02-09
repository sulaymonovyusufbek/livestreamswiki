# Wikipedia Discord Bot

This project is a Spring Boot-based bot that streams recent changes from Wikipedia and provides updates to a Discord channel. It uses Kafka for messaging and PostgreSQL for data storage, enabling users to retrieve daily statistics about Wikipedia changes.


## 🚀 Features

✅ Streams Wikipedia edits in real-time.  
✅ Supports multiple languages via the `!setLang` command.  
✅ Tracks daily edit counts with the `!stats [yyyy-mm-dd]` command.  
✅ Uses **Kafka** for event streaming.  
✅ Stores statistics in **PostgreSQL** for persistence. 



## Instalation

```bash
git clone https://github.com/sulaymonovyusufbek/livestreamswiki.git
```



### Prerequisites

 - Java 17 or higher
- Apache Kafka
- PostgreSQL
- Discord Bot Token

1. Configure application.properties

```bash
# Wikipedia Stream Configuration
wikipedia.stream.url=https://stream.wikimedia.org/v2/stream/recentchange

# Discord Bot Token
discord.bot.token=your_discord_bot_token_here

# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name
spring.datasource.username=postgres
spring.datasource.password=your_pg_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.datasource.driver-class-name=org.postgresql.Driver

# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.consumer.group-id=wikipedia-consumer-group
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
```




## Usage

Install and start your kafka.


```bash
installation guide: 
https://kafka.apache.org/quickstart

Once installed start your kafka:
bin/kafka-server-start.sh config/server.properties

```


User Maven to build this project 
```bash
mvn clean install
```

Run the application
```bash
./mvnw spring-boot:run
```



## How to Use the Bot
1. Set Languages (By default En - English)

Language Codes:  English - enwiki,  Russian -ruwiki,  Spanish - eswiki, Germany-dewiki
```bash
Command: !setLang <language_code>
Example: !setLang enwiki
```

2. Stream Recent Changes:

```bash
Command: !recent
Note: Streams programmed to be delived in 3 sec interval in bot
```

3.Stop streams:

```bash
Command: !stop
Note: stops stream if pause is need
```


3. Track the number of changes per day for chosen language.

```bash
Command: !setLang ruwiki
Command: !stats <yyyy-mm-dd>
Example: !stats  2025-02-09 
```



Describe the tools needed to deploy a new project.

## Technologies

* [Spring](https://spring.io/) - Framework Used.
* [Discort](https://discord.com/developers/applications) - Stream consuming application.
* [Hibernate](https://hibernate.org/) - ORM.
* [Postgresql](https://www.postgresql.org/download/linux/debian/) - Stats storing Database 
* [Kafka](https://kafka.apache.org/quickstart) - Message broker for scaling 

## Design Decisions

## Spring boot
- It provides built-in Kafka integration, making event-driven processing easy.

- It simplifies database access with Spring Data JPA.

- It allows rapid development with minimal configuration.

## Kafka

Kafka is used to process and handle messages efficiently between the Wikipedia stream and Discord.Topic partitions help to handle large amount of data. It decouples data processing, allowing for real-time streaming of Wikipedia changes and handling of large-scale events.

## Postgres

 Daily statistics are saved in PostgreSQL for persistence. This enables historical tracking and retrieval of Wikipedia changes per language.

## Discord Interaction

The bot listens to commands like !setLang and !stats, !stops which directly interact with users. Messages are sent to Discord channels via JDA (Java Discord API).

## Language Management:

Users can set their preferred Wikipedia language via the !setLang command, which will impact their personal stream and stats data.
