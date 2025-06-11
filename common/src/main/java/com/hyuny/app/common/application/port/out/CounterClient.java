package com.hyuny.app.common.application.port.out;

public interface CounterClient {
    void increment(String name, String... tags);
}
