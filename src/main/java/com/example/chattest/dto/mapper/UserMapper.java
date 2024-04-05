package com.example.chattest.dto.mapper;

import com.example.chattest.dto.UserDto;
import com.example.chattest.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto map(User user) {
        return new UserDto(user.getId(), user.getUsername());
    }
}
