package com.example.chattest.controller;

import com.example.chattest.api.MessageService;
import com.example.chattest.dto.CreateMessageRequest;
import com.example.chattest.dto.FetchMessageRequest;
import com.example.chattest.dto.FetchMessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/message")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<FetchMessageResponse> createMessage(@Valid @RequestBody CreateMessageRequest request) {
        return new ResponseEntity<>(
                messageService.createNewMessage(request.chatRoomId(), request.userName(), request.text()),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<FetchMessageResponse> fetchMessage(@RequestBody FetchMessageRequest request) {
        return new ResponseEntity<>(
                messageService.fetchHistory(request.chatRoomId(), request.userName()),
                HttpStatus.OK
        );
    }
}
