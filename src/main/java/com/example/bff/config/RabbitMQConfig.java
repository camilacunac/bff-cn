package com.example.bff.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "cn_service";

    @Bean
    public Queue alertasQueue() {
        return new Queue(QUEUE_NAME, true);
    }
}
