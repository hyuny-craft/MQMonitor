package demo.app.core.domain.event;

public interface EventConsumeHandler<T extends DomainEvent> {
    void consume(T event);
    Class<T> getEventType();
}
