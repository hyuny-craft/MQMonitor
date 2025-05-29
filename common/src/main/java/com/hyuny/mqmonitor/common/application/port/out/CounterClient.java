package com.hyuny.mqmonitor.common.application.port.out;

public interface CounterClient {
    void increment(String name, String... tags);
}
