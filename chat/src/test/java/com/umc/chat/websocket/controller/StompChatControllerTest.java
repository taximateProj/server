package com.umc.chat.websocket.controller;

import com.umc.chat.domain.dto.ChatMessageDto;
import com.umc.chat.chatting.chatproducer.KafkaChatProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class StompChatControllerTest {

    @Mock
    private SimpMessagingTemplate template;

    @Mock
    private KafkaChatProducer kafkaProducer;

    @InjectMocks
    private StompChatController stompChatController;

    @Captor
    private ArgumentCaptor<ChatMessageDto> messageCaptor;

    @Captor
    private ArgumentCaptor<String> topicCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMessage() {
        // given
        ChatMessageDto message = ChatMessageDto.builder()
                .roomId("room1")
                .sender("user1")
                .content("Hello, World!")
                .build();

        // when
        stompChatController.message(message);

        // then
        verify(kafkaProducer).send(topicCaptor.capture(), messageCaptor.capture());
        assertThat(topicCaptor.getValue()).isEqualTo("kafka.chat");
        assertThat(messageCaptor.getValue().getRoomId()).isEqualTo("room1");
        assertThat(messageCaptor.getValue().getSender()).isEqualTo("user1");
        assertThat(messageCaptor.getValue().getContent()).isEqualTo("Hello, World!");
    }
}