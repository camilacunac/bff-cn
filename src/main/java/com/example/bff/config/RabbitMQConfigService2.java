package com.example.bff.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigService2 {

    public static final String QUEUE_NAME_2 = "cn_service2";

    @Bean
    public Queue reportesQueue() {
        return new Queue(QUEUE_NAME_2, true);
    }
}
