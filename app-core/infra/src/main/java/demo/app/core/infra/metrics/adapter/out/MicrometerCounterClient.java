package demo.app.core.infra.metrics.adapter.out;

import demo.app.core.infra.kafka.adapter.out.CounterClient;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MicrometerCounterClient implements CounterClient {
    private final MeterRegistry registry;

    @Override
    public void increment(String name, String... tags) {
        registry.counter(name, tags).increment();
    }
}
