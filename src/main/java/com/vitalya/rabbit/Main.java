package com.vitalya.rabbit;

import com.rabbitmq.client.ConnectionFactory;
import com.vitalya.rabbit.publisher.RabbitMessagePublisher;

public class Main {
    private static final String host = "localhost";
    public static final String FIRST_QUEUE = "firstQueue";
    public static final String SECOND_QUEUE = "secondQueue";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        RabbitMessagePublisher rabbitMessagePublisher = new RabbitMessagePublisher(connectionFactory);
        rabbitMessagePublisher.publishMessage("Hi from first queue", FIRST_QUEUE);
        rabbitMessagePublisher.publishMessage("Hi from second queue", SECOND_QUEUE);
    }
}
