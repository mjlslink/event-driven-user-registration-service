package org.example.user;

import org.example.user.events.UserRegistrationListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventDrivenUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventDrivenUserApplication.class, args);
    }

    @Bean
    public UserRegistrationListener userRegistrationListener() {
        return new UserRegistrationListener();
    }

}
