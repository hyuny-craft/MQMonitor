package demo.app.core.infra.kafka.adapter.out;

public interface CounterClient {
    void increment(String name, String... tags);
}
