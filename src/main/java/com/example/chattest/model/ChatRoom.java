package com.example.chattest.model;

import com.example.chattest.model.user.User;
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

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    public Long getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<User> getUsers() {
        return users;
    }
}
