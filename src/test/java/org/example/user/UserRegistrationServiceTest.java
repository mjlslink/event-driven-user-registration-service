package org.example.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRegistrationServiceTest {

    @Autowired
    private UserRegistrationService service;

    @Test
    public void testRegistration() {
        service.registerUser("mjlslink");
    }
}
