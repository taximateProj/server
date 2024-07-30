package com.umc.chat.chatting.chatproducer;

import com.umc.chat.domain.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaChatProducer {

    private final KafkaTemplate<String, ChatMessageDto> kafkaTemplate;

    // 주어진 토픽으로 메시지를 전송
    public void send(String topic, ChatMessageDto messageDto) {
        log.info("KafkaProducer.sendTopic : " + topic); // 전송할 토픽을 로그에 출력
        log.info("KafkaProducer.messageContent : roomId={}, writer={}, message={}: ", messageDto.getRoomId(), messageDto.getSender(), messageDto.getContent()); // 메시지 내용을 로그에 출력
        kafkaTemplate.send(topic, messageDto); // Kafka 토픽에 메시지를 전송
    }
}

/**
 * Kafka 토픽으로 메시지를 전송
 * 주요 역할:
 *
 * - KafkaTemplate을 사용하여 주어진 토픽으로 ChatMessage 객체를 전송
 *
 * 주요 필드:
 *
 * - KafkaTemplate<String, ChatMessage> kafkaTemplate: Kafka 토픽으로 메시지를 전송하는 데 사용
 *
 * 주요 메서드:
 *
 * 1. send(String topic, ChatMessage messageDto):
 *    - 주어진 토픽으로 메시지를 전송합니다.
 *    - 전송할 토픽과 메시지 내용을 로그에 출력합니다.
 *    - KafkaTemplate을 사용하여 메시지를 Kafka 토픽에 전송합니다.
*/