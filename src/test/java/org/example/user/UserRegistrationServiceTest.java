package org.example.user;

import org.example.user.UserRegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationServiceTest {

    @InjectMocks
    private UserRegistrationService userRegistrationService;

    @Test
    public void testEncryptDecryptWithDifferentKeys() throws Exception {
        // Generate two different keys
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey key1 = keyGen.generateKey();
        SecretKey key2 = keyGen.generateKey();

        // Encrypt with key1 and decrypt with key2
        String originalData = "Hello World";
        String encryptedData = userRegistrationService.encrypt(originalData, key1);
        String decryptedData = userRegistrationService.decrypt(encryptedData, key2);

        // The decrypted data should not match the original data
        assertNotEquals(originalData, decryptedData);
    }

    @Test
    public void testEncryptDecryptWithSameKey() throws Exception {
        // Generate a single key
        SecretKey key = userRegistrationService.getSecretKey();

        // Encrypt and decrypt with the same key
        String originalData = "Hello World";
        String encryptedData = userRegistrationService.encrypt(originalData, key);
        String decryptedData = userRegistrationService.decrypt(encryptedData, key);

        // The decrypted data should match the original data
        assertEquals(originalData, decryptedData);
    }
}