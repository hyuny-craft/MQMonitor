package com.hyuny.mqmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hyuny.mqmonitor")
public class MessageCommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageCommandServiceApplication.class, args);
    }

}
