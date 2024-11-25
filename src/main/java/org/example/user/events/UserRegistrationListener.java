
package org.example.user.events;

import org.example.user.EmailService;
import org.example.user.UserRegistrationService;
import org.example.user.db.UserRegistrationRepository;
import org.example.user.models.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);

    @Autowired
    private UserRegistrationRepository repository;

    @Autowired
    private EmailService emailService;

    @EventListener
    public void handleUserRegistration(UserRegistrationEvent event) {
        logger.info("User registered: {}", event.getUsername());
        // Perform actions after user registration, e.g., send a welcome email
        UserEntity userEntity = new UserEntity();
        userEntity.setName(event.getUsername());
        UserEntity savedUser = repository.saveAndFlush(userEntity);
        emailService.sendWelcomeEmail(savedUser.getName());
    }
}
