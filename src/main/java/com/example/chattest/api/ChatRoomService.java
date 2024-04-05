package com.example.chattest.api;


import com.example.chattest.dto.ChatRoomDto;

public interface ChatRoomService {
    ChatRoomDto getById(Long id);
}
