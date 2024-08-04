package com.umc.chat.chatting.chatproducer;

import com.umc.chat.domain.dto.ChatMessageDto;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class KafkaChatProducerTest {

    @Mock
    private KafkaTemplate<String, ChatMessageDto> kafkaTemplate;

    @InjectMocks
    private KafkaChatProducer kafkaChatProducer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSend() {
        // Given
        String topic = "testTopic";
        ChatMessageDto messageDto = new ChatMessageDto("room1", "user1", "Hello, World!");

        // When
        kafkaChatProducer.send(topic, messageDto);

        // Then
        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ChatMessageDto> messageCaptor = ArgumentCaptor.forClass(ChatMessageDto.class);
        verify(kafkaTemplate).send(topicCaptor.capture(), messageCaptor.capture());

        assertEquals(topic, topicCaptor.getValue());
        assertEquals(messageDto, messageCaptor.getValue());
    }
}