package com.example.chattest.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class FetchMessageResponse {
    private List<CreateMessageResponse> messages = new ArrayList<>();

    public FetchMessageResponse(List<CreateMessageResponse> collect) {
        this.messages.addAll(collect);
    }
}
