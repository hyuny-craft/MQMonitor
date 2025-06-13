package demo.app.core.infra.kafka.adapter.out;

public interface MessageProducer {
    <T> T produce(T message);
}
