package org.example.user;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
class UserRegistrationEvent extends ApplicationEvent {
    private String username;

    public UserRegistrationEvent(String username) {
        super(username);
        this.username = username;
    }

}