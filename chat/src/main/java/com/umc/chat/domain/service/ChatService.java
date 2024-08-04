package com.umc.chat.domain.service;

import com.umc.chat.domain.dto.ChatMessageDto;
import com.umc.chat.domain.entity.ChatMessage;
import com.umc.chat.domain.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatMessageRepository chatRepository;

    public void saveMessage(ChatMessageDto message) {
        ChatMessage chatMessage = message.toEntity();
        chatRepository.save(chatMessage);
    }
}
