package com.example.chattest.controller;

import com.example.chattest.api.ChatRoomService;
import com.example.chattest.dto.ChatRoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


@RestController("/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatRoomDto> getRoomById(@PathVariable Long id) {
        return new ResponseEntity<>(chatRoomService.getById(id), HttpStatus.OK);
    }
}
