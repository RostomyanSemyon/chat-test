package com.example.chattest.dto;

import jakarta.validation.constraints.Size;

public record CreateMessageRequest(
        Long chatRoomId,
        String userName,
        @Size(max = 200)
        String text
) {
}
