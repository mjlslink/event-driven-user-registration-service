package org.example.user;

import org.example.user.db.UserRegistrationRepository;
import org.example.user.events.UserRegistrationEvent;
import org.example.user.exceptions.UserAlreadyExistsException;
import org.example.user.models.User;
import org.example.user.models.UserEntity;
import org.example.user.models.UserModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public final class UserRegistrationService {

    private final static Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);

    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserRegistrationRepository repository;
    private final UserModelMapper userModelMapper;
    private SecretKey secretKey;

    public UserRegistrationService(ApplicationEventPublisher applicationEventPublisher, UserRegistrationRepository repository, UserModelMapper userModelMapper) throws Exception {
        this.applicationEventPublisher = applicationEventPublisher;
        this.repository = repository;
        this.userModelMapper = userModelMapper;
        // Generate the key only once when the service is created
        this.secretKey = generateSecretKey();
    }

    private SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    public User registerUser(String username) {
        Optional<UserEntity> existingUser = repository.findByName(username);

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with username: " + username);
        }

        applicationEventPublisher.publishEvent(new UserRegistrationEvent(username));

        UserEntity userEntity = new UserEntity();
        userEntity.setName(username);
        UserEntity savedUser = repository.saveAndFlush(userEntity);

        logger.info("User {} has been registered", username);

        return userModelMapper.toUser(savedUser);
    }

    public List<User> getAllUser() {
        return repository.findAll().stream().map(userModelMapper::toUser).toList();
    }
}