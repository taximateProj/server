package com.umc.chat.chatting.chatconsumer;

import com.umc.chat.domain.dto.ChatMessageDto;
import com.umc.chat.domain.service.ChatService;
import com.umc.chat.kafkaconfig.KafkaConsts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaChatConsumer {
    private final SimpMessagingTemplate template; // 특정 Broker로 메시지를 전달하는 역할을 합니다.// 채팅 서비스와 상호작용합니다.
    private final ChatService chatService;

    // Kafka 토픽에서 메시지를 소비하는 메서드입니다.
    @KafkaListener(topics = KafkaConsts.CHAT_TOPIC, groupId = KafkaConsts.CHAT_GROUP_ID, containerFactory = "kafkaChatListenerContainerFactory")
    public void consumeChatMessage(ChatMessageDto message) throws IOException {
        log.info("KafkaConsumer.consumeMessage");
        log.info("KafkaConsumer.messageContent : roomId={}, writer={}, message={}: ", message.getRoomId(), message.getSender(), message.getContent());

        // 메시지 내용을 로그에 출력합니다.
        HashMap<String, String> msg = new HashMap<>();
        msg.put("roomName", String.valueOf(message.getRoomId()));
        msg.put("message", message.getContent());
        msg.put("writer", message.getSender());

        chatService.saveMessage(message);

        // STOMP를 통해 특정 채팅방에 메시지를 전송합니다.
        template.convertAndSend("/subscribe/chat/room/" + message.getRoomId(), message); // STOMP 토픽으로 메시지 전송
    }
}

/**
 * Kafka에서 메시지를 소비하고 이를 WebSocket을 통해 브로드캐스트
 * 주요 필드:
 *
 * - SimpMessagingTemplate template: STOMP를 통해 특정 Broker로 메시지를 전달
 *
 * 주요 메서드:
 *
 * 1. consume(ChatMessage message):
 *    - @KafkaListener: 이 메서드는 Kafka 토픽에서 메시지를 소비
 *    - 메시지를 소비하면 로그에 메시지 내용을 출력
 *    - STOMP를 통해 "/sub/chat/room/{roomId}" 경로로 메시지를 전송하여 해당 채팅방에 있는 모든 클라이언트에게 메시지를 전달
 *
 * 상호작용 요소:
 *
 * - 클라이언트가 Kafka 토픽에 메시지를 게시하면, 이 메서드가 호출되어 메시지를 소비
 * - 소비된 메시지는 로그에 출력되고, STOMP를 통해 특정 채팅방으로 브로드캐스트
 * - STOMP를 통해 메시지가 "/sub/chat/room/{roomId}" 경로로 전송되어 해당 채팅방에 있는 모든 클라이언트에게 전달
 *
 * 이 클래스는 Kafka와 WebSocket 간의 브릿지 역할을 수행하여, 메시지 소비와 브로드캐스트를 처리
 */
