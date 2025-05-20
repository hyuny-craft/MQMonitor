package com.hyuny.mqmonitor.application;

import com.hyuny.mqmonitor.adapter.out.mq.KafkaStatusMessageClient;
import com.hyuny.mqmonitor.domain.Message;
import com.hyuny.mqmonitor.domain.port.out.StatusMessageClient;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MessageProcessorTest {
    private StatusMessageClient statusClient;
    private SimpleMeterRegistry registry;
    private MessageProcessor processor;

    @BeforeEach
    void setUp() {
        statusClient = mock(KafkaStatusMessageClient.class);
        registry = new SimpleMeterRegistry();
        processor = new MessageProcessor(statusClient, registry);
    }

    @Test
    void 성공_처리시_카운터증가_및_상태전송() {
        Message message = new Message("id-123", "ok");
        processor.process(message);

        Counter counter = registry.find("mqmonitor.message.processing.count")
                .tag("result", "SUCCESS").counter();

        assertThat(counter).isNotNull();
        assertThat(counter.count()).isEqualTo(1.0);
        verify(statusClient).publishStatus("id-123", "SUCCESS", "");
    }

    @Test
    void 실패_처리시_카운터증가_및_상태전송() {
        // given
        doThrow(new IllegalStateException("전송 실패")).when(statusClient).publishStatus(anyString(), eq("SUCCESS"), anyString());
        Message message = new Message("id-456", "정상처리지만_전송실패");

        // when
        processor.process(message);

        // then
        Counter counter = counter = registry.find("mqmonitor.message.processing.count")
                .tag("result", "FAILURE")
                .tag("reason", "IllegalStateException")
                .counter();

        assertThat(counter).isNotNull();
        assertThat(counter.count()).isEqualTo(1.0);

        verify(statusClient).publishStatus(eq("id-456"), eq("FAILURE"), eq("전송 실패"));    }
}