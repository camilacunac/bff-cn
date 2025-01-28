package com.example.bff.model;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.bff.config.RabbitMQConfig;
import com.example.bff.util.AlertWebSocketHandler;

@Component
public class AlertListener {

    private final AlertWebSocketHandler webSocketHandler;

    public AlertListener(AlertWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void listen(String message) {
        System.out.println("Mensaje recibido desde RabbitMQ: " + message);
        webSocketHandler.sendMessageToAll(message);
    }
}
