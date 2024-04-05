package com.example.chattest.dto.mapper;


import com.example.chattest.dto.ChatRoomDto;
import com.example.chattest.model.ChatRoom;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomMapper {

    public ChatRoomDto map(ChatRoom chatRoom) {
        return new ChatRoomDto(chatRoom.getId());
    }
}
