package com.hyuny.app.publisher.adapter.out.kafka;

import com.hyuny.app.common.application.port.out.MessageProducer;
import com.hyuny.app.common.domain.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessagePublisher implements MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value( "${app.kafka.topic}")
    private String topic;

    public KafkaMessagePublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void produce(Message message) {
        kafkaTemplate.send(topic, message.id(), message.payload());
    }
}
