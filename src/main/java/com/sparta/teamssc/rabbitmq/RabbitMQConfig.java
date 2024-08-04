package com.sparta.teamssc.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 일반 큐
    public static final String QUEUE_NAME = "chat-queue";
    // 데드레터 큐
    public static final String DEAD_LETTER_QUEUE = "chat-queue-dlq";

    //교환기
    public static final String DEAD_LETTER_EXCHANGE = "chat-queue-dlx";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }
}