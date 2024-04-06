package com.example.chattest.api;

import com.example.chattest.dto.FetchMessageResponse;

public interface MessageService {
    FetchMessageResponse createNewMessage(Long chatRoomId, String userName, String text);

    FetchMessageResponse fetchHistory(Long chatRoomId, String userName);
}
