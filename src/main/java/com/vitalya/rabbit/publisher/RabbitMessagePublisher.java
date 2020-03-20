package com.vitalya.rabbit.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RabbitMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMessagePublisher.class);

    private final ConnectionFactory connectionFactory;

    public RabbitMessagePublisher(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void publishMessage(String message, String queueName) {

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, null, message.getBytes());

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
