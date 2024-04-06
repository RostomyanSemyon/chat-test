package com.example.chattest.model;

import com.example.chattest.model.message.Message;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;

    public Long getId() {
        return id;
    }
}
