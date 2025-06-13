package demo.app.core.domain.event;

import demo.app.core.domain.model.MessageStatus;

import java.time.Instant;

public record MessageSentEvent(
        String id,
        MessageStatus status,
        String reason,
        Instant occurredAt
) implements DomainEvent {}
