package com.example.chattest.service;

import com.example.chattest.api.MessageService;
import com.example.chattest.clock.Clock;
import com.example.chattest.dto.CreateMessageResponse;
import com.example.chattest.dto.FetchMessageResponse;
import com.example.chattest.model.FetchHistoryEvent;
import com.example.chattest.model.message.Message;
import com.example.chattest.repository.ChatRoomRepository;
import com.example.chattest.repository.FetchEventHistoryRepository;
import com.example.chattest.repository.MessageRepository;
import com.example.chattest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final FetchEventHistoryRepository fetchEventHistoryRepository;
    private final UserRepository userRepository;

    private final Clock clock;

    @Autowired
    public MessageServiceImpl(
            MessageRepository messageRepository,
            ChatRoomRepository chatRoomRepository,
            FetchEventHistoryRepository fetchEventHistoryRepository,
            UserRepository userRepository,
            Clock clock
    ) {
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.fetchEventHistoryRepository = fetchEventHistoryRepository;
        this.userRepository = userRepository;
        this.clock = clock;
    }

    @Override
    @Transactional
    public FetchMessageResponse createNewMessage(Long chatRoomId, String userName, String text) {
        FetchMessageResponse fetchMessageResponse = fetchHistory(chatRoomId, userName);

        Message message = Message.builder()
                .chatRoom(chatRoomRepository.findById(chatRoomId)
                        .orElseThrow(() -> new RuntimeException("Room was not found")))
                .user(userRepository.findUserByUserName(userName)
                        .orElseThrow(() -> new RuntimeException("User was not found")))
                .sendTime(clock.now())
                .text(text)
                .build();
        messageRepository.save(message);

        //TODO почему возвращаются все сообщения
        fetchMessageResponse.getMessages().add(new CreateMessageResponse(userName, text));
        return fetchMessageResponse;
    }

    @Override
    @Transactional
    public FetchMessageResponse fetchHistory(Long chatRoomId, String userName) {
        LocalDateTime currentDate = clock.now();

        FetchHistoryEvent fetchHistoryEvent = fetchEventHistoryRepository.findFirstByChatRoomIdAndUserNameAndLastFetchDateBeforeOrderByLastFetchDateDesc(chatRoomId, userName, currentDate);

        var messages = new ArrayList<Message>();
        if (fetchHistoryEvent == null) {
            messages.addAll(messageRepository.findMessagesBySendTimeBefore(currentDate));
        } else {
            messages.addAll(messageRepository.findMessagesBySendTimeBetween(fetchHistoryEvent.getLastFetchDate(), currentDate));
        }

        FetchHistoryEvent newFetchHistoryEvent = FetchHistoryEvent.builder()
                .userName(userName)
                .lastFetchDate(currentDate)
                .chatRoomId(chatRoomId) //TODO здесь возвращается null
                .build();

        fetchEventHistoryRepository.save(newFetchHistoryEvent);

        return new FetchMessageResponse(
                messages
                        .stream()
                        .map(e -> new CreateMessageResponse(e.getUser().getUsername(), e.getText()))
                        .collect(Collectors.toList())
        );
    }
}
