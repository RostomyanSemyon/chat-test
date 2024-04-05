package com.example.chattest.api;


import com.example.chattest.dto.UserDto;

public interface UserService {
    UserDto getUserByName(String userName);
}
