// UserModelMapper.java

package org.example.user.models;

import org.example.user.models.User;
import org.example.user.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapper {

    public User toUser(UserEntity userEntity) {
        return new User(userEntity.getName());
    }
}