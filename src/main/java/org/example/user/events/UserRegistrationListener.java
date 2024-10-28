package org.example.user.events;

import org.example.user.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener implements ApplicationListener<UserRegistrationEvent> {

    private static Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);

    @EventListener
    public void handleUserRegistration(UserRegistrationEvent event) {
        logger.info("User registered: {}", event.getUsername());
        // Perform actions after user registration, e.g., send a welcome email
    }

    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {

    }
}