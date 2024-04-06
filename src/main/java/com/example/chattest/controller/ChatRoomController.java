package com.example.chattest.controller;

import com.example.chattest.api.ChatRoomService;
import com.example.chattest.dto.ChatRoomDto;
import com.example.chattest.dto.JoinRoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("api/v1/room")
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

    @PostMapping
    public ResponseEntity<JoinRoomDto> getRoomById(@RequestBody JoinRoomDto dto) {
        return new ResponseEntity<>(chatRoomService.joinUserToRoom(dto), HttpStatus.OK);
    }
}
