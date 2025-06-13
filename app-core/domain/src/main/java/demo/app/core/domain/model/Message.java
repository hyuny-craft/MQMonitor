package demo.app.core.domain.model;

import demo.app.core.domain.event.MessageSentEvent;

import java.time.Instant;

public record Message(String id, String payload) {

    public boolean isValid(){
        return id != null && !id.isBlank() && payload != null && !payload.isBlank();
    }


    public MessageSentEvent toEvent() {
        return new MessageSentEvent(id, MessageStatus.START, payload, Instant.now());
    }

}
