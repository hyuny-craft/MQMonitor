package demo.app.core.infra.kafka.dto;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import demo.app.core.domain.event.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Getter
@ToString
@EqualsAndHashCode
public class FailedEvent<T extends DomainEvent> {
    private final String eventType;
    private final String eventId;
    private final T payload;
    private final Instant occurredAt;

    public FailedEvent(String eventType, T payload) {
        this.eventType = eventType;
        this.eventId = UUID.randomUUID().toString();
        this.payload = payload;
        this.occurredAt = Instant.now();
    }

    public String toJsonString() {
        try {
            return (new ObjectMapper().registerModule(new JavaTimeModule())).writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.warn("메시지 직렬화 실패: {}", e.getMessage(), e);
        }
        return "";
    }
}
