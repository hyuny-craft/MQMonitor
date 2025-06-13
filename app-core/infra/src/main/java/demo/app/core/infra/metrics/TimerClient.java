package demo.app.core.infra.metrics;

public interface TimerClient {
    void timed(String metricName, Runnable task);
}
