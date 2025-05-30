package com.hyuny.mqmonitor.utils;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

public class TimerUtils {
    public static void timed(MeterRegistry registry, String metricName, Runnable task) {
        Timer.builder(metricName)
                .description("Auto-timed task")
                .publishPercentiles(0.5, 0.95, 0.99)
                .publishPercentileHistogram(true)
                .register(registry)
                .record(task);
    }
}
