package com.example.chattest.controller;

import com.example.chattest.api.UserService;
import com.example.chattest.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String userName) {
        return new ResponseEntity<>(userService.getUserByName(userName), HttpStatus.OK);
    }
}
