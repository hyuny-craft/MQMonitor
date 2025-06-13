package demo.app.api.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "demo.app")
public class MessageCommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageCommandServiceApplication.class, args);
    }

}
