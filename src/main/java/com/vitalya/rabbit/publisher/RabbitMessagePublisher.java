package com.vitalya.rabbit.publisher;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RabbitMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMessagePublisher.class);

    private final Channel channel;

    public RabbitMessagePublisher(Channel channel) {
        this.channel = channel;
    }

    public void publishMessage(String message, String queueName) {
        try {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, null, message.getBytes());
            logger.info("Message {} pushed into queue {}", message, queueName);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
