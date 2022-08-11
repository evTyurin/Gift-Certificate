package com.epam.esm.builder;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {
    public User build(UserDto userDto) {
        return User
                .builder()
                .name(userDto.getName())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .createDate(userDto.getCreateDate())
                .build();
    }

    public UserDto build(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .password(user.getPassword())
                .createDate(user.getCreateDate())
                .build();
    }
}