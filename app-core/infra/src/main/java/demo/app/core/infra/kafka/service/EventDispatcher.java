package demo.app.core.infra.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.app.core.domain.event.DomainEvent;
import demo.app.core.domain.event.EventConsumeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 추상화 + 실패/로깅 통제 지점
 * 역직렬화 + 라우팅
 */
@Component
@Slf4j
public class EventDispatcher {
    private final ObjectMapper mapper;
    private final Map<String, EventConsumeHandler<? extends DomainEvent>> handlerMap;

    public EventDispatcher(ObjectMapper mapper, List<EventConsumeHandler<? extends DomainEvent>> handlers) {
        this.mapper = mapper;
        this.handlerMap = new HashMap<>();
        for (var handler : handlers) {
            System.out.println("handler = " + handler.getEventType().getSimpleName());
            handlerMap.put(handler.getEventType().getSimpleName(), handler);
        }

    }

    public void route(String messagePayload, String key) {
        try {
            EventConsumeHandler<? extends DomainEvent> handler = handlerMap.get(key);
            if (handler == null) {
                log.warn("No handler found: {}", key);
                return;
            }
            Class<? extends DomainEvent> clazz = handler.getEventType();
            DomainEvent event = mapper.readValue(messagePayload, clazz);
            invokeConsume(handler, event);
        } catch (JsonProcessingException e) {
            // TODO 실패 이벤트 저장소에 기록하거나 DLQ로 보내거나
            log.error("Failed to dispatch event", e);
        }

    }

    private <T extends DomainEvent> void invokeConsume(EventConsumeHandler<T> handler, DomainEvent event) {
        handler.consume((T) event);
    }


}
