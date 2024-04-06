package com.example.chattest.model;

import com.example.chattest.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class FetchHistoryEvent {
    @Id
    @GeneratedValue
    private Long id;

    private String userName;

    private LocalDateTime lastFetchDate;

    private Long chatRoomId;
}
