package com.hyuny.mqmonitor.adapter.out.metrics;

import com.hyuny.mqmonitor.common.metrics.TimerClient;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MicrometerTimerClient implements TimerClient {

    private final MeterRegistry registry;

    @Override
    public void timed(String metricName, Runnable task) {
        Timer.builder(metricName)
                .description("Auto-timed task")
                .publishPercentiles(0.5, 0.95, 0.99)
                .publishPercentileHistogram(true)
                .register(registry)
                .record(task);
    }

}
