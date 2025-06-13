package demo.app.core.infra.kafka.adapter.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.app.core.domain.event.DomainEvent;
import demo.app.core.domain.event.EventPublishPort;
import demo.app.core.domain.event.MessageSentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaEventPublisher implements EventPublishPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Value("main-topic")
    private String topic;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(DomainEvent event) {
        MessageSentEvent _event = (MessageSentEvent) event;
        try {
            String json = objectMapper.writeValueAsString(_event);
            kafkaTemplate.send(
                    topic,
                    _event.getClass().getSimpleName(),
                    json);
        } catch (JsonProcessingException e) {
            log.warn("이벤트 직렬화 실패: {}", e.getMessage());
        }
    }
}
