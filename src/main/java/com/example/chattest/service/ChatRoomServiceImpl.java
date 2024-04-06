package com.example.chattest.service;

import com.example.chattest.api.ChatRoomService;
import com.example.chattest.dto.ChatRoomDto;
import com.example.chattest.dto.JoinRoomDto;
import com.example.chattest.dto.mapper.ChatRoomMapper;
import com.example.chattest.model.ChatRoom;
import com.example.chattest.model.user.User;
import com.example.chattest.repository.ChatRoomRepository;
import com.example.chattest.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;
    private final UserRepository userRepository;

    @Autowired
    public ChatRoomServiceImpl(
            ChatRoomRepository chatRoomRepository,
            ChatRoomMapper chatRoomMapper,
            UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomMapper = chatRoomMapper;
        this.userRepository = userRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        ChatRoom initialChatRoom = new ChatRoom();
        chatRoomRepository.save(initialChatRoom);
    }

    @Override
    @Transactional
    public ChatRoomDto getById(Long id) {
        return chatRoomMapper.map(
                chatRoomRepository.findById(id).orElseThrow(EntityNotFoundException::new)
        );
    }

    @Override
    @Transactional
    public JoinRoomDto joinUserToRoom(JoinRoomDto dto) {
        ChatRoom chatRoom = chatRoomRepository.findById(dto.chatRoomId()).orElseThrow(()-> new RuntimeException("Room was not found"));
        User user = userRepository.findUserByUserName(dto.userName()).orElseThrow(()-> new RuntimeException("User was not found"));

        user.setChatRoom(chatRoom);
        return dto;
    }
}
