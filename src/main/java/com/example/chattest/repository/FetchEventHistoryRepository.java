package com.example.chattest.repository;

import com.example.chattest.model.FetchHistoryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface FetchEventHistoryRepository extends JpaRepository<FetchHistoryEvent, Long> {
    FetchHistoryEvent findFirstByChatRoomIdAndUserNameAndLastFetchDateBeforeOrderByLastFetchDateDesc(Long chatRoomId, String userName, LocalDateTime localDateTime);
}
