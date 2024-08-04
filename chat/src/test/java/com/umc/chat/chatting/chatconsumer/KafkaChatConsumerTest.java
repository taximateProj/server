package com.umc.chat.chatting.chatconsumer;

import com.umc.chat.domain.dto.ChatMessageDto;
import com.umc.chat.domain.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class KafkaChatConsumerTest {

    @Mock
    private SimpMessagingTemplate template;

    @Mock
    private ChatService chatService;

    @InjectMocks
    private KafkaChatConsumer kafkaChatConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsumeChatMessage() throws IOException {
        // Given
        ChatMessageDto message = new ChatMessageDto("room1", "user1", "Hello, World!");

        // When
        kafkaChatConsumer.consumeChatMessage(message);

        // Then
        ArgumentCaptor<ChatMessageDto> messageCaptor = ArgumentCaptor.forClass(ChatMessageDto.class);
        verify(chatService).saveMessage(messageCaptor.capture());
        assertEquals(message, messageCaptor.getValue());

        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        verify(template).convertAndSend(destinationCaptor.capture(), messageCaptor.capture());
        assertEquals("/subscribe/chat/room/room1", destinationCaptor.getValue());
        assertEquals(message, messageCaptor.getValue());
    }
}