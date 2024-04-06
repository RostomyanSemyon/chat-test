package com.example.chattest.repository;

import com.example.chattest.model.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesBySendTimeBetween(LocalDateTime lastFetchDate, LocalDateTime currentDate);
    List<Message> findMessagesBySendTimeBefore(LocalDateTime currentDate);
}
