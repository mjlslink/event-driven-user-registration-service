package org.example.user;

import org.example.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.List;

@RestController
public class UserController {

    private final UserRegistrationService service;

    @Autowired
    public UserController(UserRegistrationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(service.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/{encryptedPassword}")
    public ResponseEntity<String> checkPassword(@PathVariable String encryptedPassword) throws Exception { 
        // Get the key used for encryption from the service
        SecretKey secretKey = service.getSecretKey();
        return new ResponseEntity<String>(service.decrypt(encryptedPassword, secretKey), HttpStatus.OK); 
    }

    @ExceptionHandler(NoSuchMethodException.class)
    public ErrorResponse notFOund(NoSuchMethodException exception) {
        return ErrorResponse.create(exception, HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
