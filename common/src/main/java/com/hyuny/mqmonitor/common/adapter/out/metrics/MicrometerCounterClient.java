package com.hyuny.mqmonitor.common.adapter.out.metrics;

import com.hyuny.mqmonitor.common.application.port.out.CounterClient;
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
