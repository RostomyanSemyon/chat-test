package com.example.chattest.dto;

import jakarta.validation.constraints.Size;

public record CreateMessageResponse (
    String userName,
    @Size(max = 200)
    String text
){}

