package org.example.user.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {
    private String username;

    public UserRegistrationEvent(String username) {
        super(username);
        this.username = username;
    }

}