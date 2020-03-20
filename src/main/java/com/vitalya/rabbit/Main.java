package com.vitalya.rabbit;

import com.vitalya.rabbit.config.RabbitConfig;
import com.vitalya.rabbit.publisher.RabbitMessagePublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.IntStream;

public class Main {
    public static final String FIRST_QUEUE = "firstQueue";
    public static final String SECOND_QUEUE = "secondQueue";

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);
        RabbitMessagePublisher rabbitMessagePublisher = context.getBean(RabbitMessagePublisher.class);
        IntStream.range(0, 10)
                .forEach(index -> {
                            rabbitMessagePublisher.publishMessage(
                                    String.format("Message %d into queue %s", index, FIRST_QUEUE),
                                    FIRST_QUEUE
                            );
                            rabbitMessagePublisher.publishMessage(
                                    String.format("Message %d into queue %s", index, SECOND_QUEUE),
                                    SECOND_QUEUE);
                        }
                );

    }
}
