package com.firstway.orderservice.messaging;

import com.firstway.orderservice.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventProducer.class);
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Value("${app.kafka.topic.order-created}")
    private String topic;

    public OrderEventProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Sending event to Kafka: {}", event.getOrderId());
        kafkaTemplate.send(topic, String.valueOf(event.getOrderId()), event);
    }
}