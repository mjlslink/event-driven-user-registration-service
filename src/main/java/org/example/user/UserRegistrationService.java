package org.example.user;

import org.example.user.events.UserRegistrationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public final class UserRegistrationService {
    private final ApplicationEventPublisher applicationEventPublisher;

    private static Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);

    public UserRegistrationService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void registerUser(String username) {

        // Logic for registering the user
        logger.info("Registering user: {} ", username);

        // Publish the event
        applicationEventPublisher.publishEvent(new UserRegistrationEvent(username));
    }
}