package com.vitalya.rabbit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.vitalya.rabbit.publisher.RabbitMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class RabbitConfig {
    private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Bean
    public ConnectionFactory connectionFactory() {
        RabbitSettings rabbitSettings = rabbitSettings();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitSettings.getHost());
        connectionFactory.setVirtualHost(rabbitSettings.getVirtualHost());
        connectionFactory.setUsername(rabbitSettings.getLogin());
        connectionFactory.setPassword(rabbitSettings.getPassword());
        return connectionFactory;
    }

    @Bean
    public RabbitSettings rabbitSettings() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        RabbitSettings rabbitSettings;
        try {
            rabbitSettings = objectMapper.readValue(new File("src/main/resources/config.yaml"), RabbitSettings.class);
            logger.info(rabbitSettings.toString());
        } catch (Exception e) {
            logger.error("Configuration cannot be read");
            throw new RuntimeException(e);
        }
        return rabbitSettings;
    }

    @Bean
    public RabbitMessagePublisher rabbitMessagePublisher() throws Exception {
        Connection connection = connectionFactory().newConnection();
        Channel channel = connection.createChannel();
        return new RabbitMessagePublisher(channel);
    }
}
