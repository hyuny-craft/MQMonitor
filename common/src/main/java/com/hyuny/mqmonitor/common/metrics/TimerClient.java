package com.hyuny.mqmonitor.common.metrics;

public interface TimerClient {
    void timed(String metricName, Runnable task);
}
