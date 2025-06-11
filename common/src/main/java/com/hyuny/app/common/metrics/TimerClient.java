package com.hyuny.app.common.metrics;

public interface TimerClient {
    void timed(String metricName, Runnable task);
}
