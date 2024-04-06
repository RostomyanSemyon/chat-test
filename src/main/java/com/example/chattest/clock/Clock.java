package com.example.chattest.clock;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class Clock {
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
