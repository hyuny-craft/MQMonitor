package demo.app.core.infra.kafka.adapter.out;

public interface StatusMessageProducer {
    void produce(String messageId, String status, String reason);
}
