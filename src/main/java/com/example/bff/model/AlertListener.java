package com.example.bff.model;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.bff.config.RabbitMQConfig;
import com.example.bff.util.AlertWebSocketHandler;

@Component
public class AlertListener {

    private final AlertWebSocketHandler webSocketHandler;

    public AlertListener(AlertWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    // Escucha alertas desde RabbitMQ
    // @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    // public void listenRabbitMQ(String message) {
    // System.out.println("📥 Mensaje recibido desde RabbitMQ: " + message);
    // // webSocketHandler.sendMessageToAll(message);
    // }

    // Escucha alertas desde Kafka (topic: alertas)
    @KafkaListener(topics = "${spring.kafka.topic.alertas}", groupId = "grupo-alertas-v2")
    public void listenKafka(String message) {
        System.out.println("📥 Mensaje recibido desde Kafka (Topic: alertas): " + message);
        webSocketHandler.sendMessageToAll(message);
    }
}
