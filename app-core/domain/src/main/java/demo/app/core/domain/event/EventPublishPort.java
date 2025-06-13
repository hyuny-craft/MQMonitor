package demo.app.core.domain.event;

public interface EventPublishPort {
    void publish(DomainEvent message);
}
