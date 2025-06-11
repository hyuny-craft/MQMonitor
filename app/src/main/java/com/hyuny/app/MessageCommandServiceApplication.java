package com.hyuny.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hyuny.app")
public class MessageCommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageCommandServiceApplication.class, args);
    }

}
