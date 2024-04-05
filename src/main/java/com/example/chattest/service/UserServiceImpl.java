package com.example.chattest.service;

import com.example.chattest.api.UserService;
import com.example.chattest.dto.UserDto;
import com.example.chattest.dto.mapper.UserMapper;
import com.example.chattest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getUserByName(String userName) {
        return userMapper.map(userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }
}
