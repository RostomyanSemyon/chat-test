package com.example.chattest.api;


import com.example.chattest.dto.ChatRoomDto;
import com.example.chattest.dto.JoinRoomDto;

public interface ChatRoomService {
    ChatRoomDto getById(Long id);

    JoinRoomDto joinUserToRoom(JoinRoomDto dto);
}
