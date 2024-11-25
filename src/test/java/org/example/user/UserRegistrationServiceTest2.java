// UserRegistrationServiceTest.java

package org.example.user;

import org.example.user.db.UserRegistrationRepository;
import org.example.user.events.UserRegistrationEvent;
import org.example.user.exceptions.UserAlreadyExistsException;
import org.example.user.models.User;
import org.example.user.models.UserEntity;
import org.example.user.models.UserModelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationServiceTest2 {

    @Mock
    private UserRegistrationRepository repository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private UserModelMapper userModelMapper;

    @InjectMocks
    private UserRegistrationService userRegistrationService;

    @Test
    void testRegisterUserSuccess() {
        // Arrange
        String username = "testUser";
        UserEntity userEntity = new UserEntity();
        userEntity.setName(username);
        when(repository.findByName(username)).thenReturn(Optional.empty());
        when(repository.saveAndFlush(any(UserEntity.class))).thenReturn(userEntity);
        when(userModelMapper.toUser(userEntity)).thenReturn(new User(username));

        // Act
        User user = userRegistrationService.registerUser(username);

        // Assert
        assertEquals(username, user.getName());
        verify(applicationEventPublisher, times(1)).publishEvent(any(UserRegistrationEvent.class));
        verify(repository, times(1)).saveAndFlush(any(UserEntity.class));
    }

    @Test
    void testRegisterUserAlreadyExists() {
        // Arrange
        String username = "testUser";
        UserEntity userEntity = new UserEntity();
        userEntity.setName(username);
        when(repository.findByName(username)).thenReturn(Optional.of(userEntity));

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userRegistrationService.registerUser(username));
        verify(applicationEventPublisher, never()).publishEvent(any(UserRegistrationEvent.class));
        verify(repository, never()).saveAndFlush(any(UserEntity.class));
    }
}