package com.umc.chat.websocket.controller;

import com.umc.chat.domain.dto.ChatMessageDto;
import com.umc.chat.kafkaconfig.KafkaConsts;
import com.umc.chat.chatting.chatproducer.KafkaChatProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {
    private final SimpMessagingTemplate template; // 특정 Broker로 메시지를 전달
    private final KafkaChatProducer kafkaProducer; // Kafka Producer를 사용하여 메시지를 Kafka 토픽에 전송

    @MessageMapping(value = "/chat/message") // 클라이언트가 "/publish/chat/message" 경로로 메시지를 발행
    public void message(ChatMessageDto message){
        log.info(message.getContent()); // 로그에 메시지를 출력합니다.
        kafkaProducer.send(KafkaConsts.CHAT_TOPIC, message); // Kafka Producer를 통해 메시지를 Kafka 토픽에 전송
    }
}

/**
 * 이 클래스는 WebSocket을 통한 채팅 메시지 처리를 담당합니다.
 * 주요 필드:
 *
 * - SimpMessagingTemplate template: STOMP를 통해 특정 Broker로 메시지를 전달
 * - KafkaProducer kafkaProducer: Kafka Producer를 사용하여 메시지를 Kafka 토픽에 전송
 *
 * 주요 메서드:
 *
 * 1. enter(ChatMessage message):
 *    - 클라이언트가 "/pub/chat/enter" 경로로 메시지를 발행할 때 호출됨
 *    - 메시지 내용을 "사용자님이 채팅방에 참여하였습니다."로 설정합니다.
 *    - STOMP를 통해 "/sub/chat/room/{roomId}" 경로로 메시지를 전송하여 해당 채팅방에 있는 모든 클라이언트에게 메시지를 전달
 *
 * 2. message(ChatMessage message):
 *    - 클라이언트가 "/pub/chat/message" 경로로 메시지를 발행할 때 호출
 *    - 메시지를 로그에 출력
 *    - Kafka Producer를 통해 메시지를 Kafka 토픽에 전송
 *
 * 상호작용 요소:
 *
 * - 클라이언트가 "/pub/chat/enter" 경로로 메시지를 발행하면, `enter` 메서드가 호출되어 메시지가 특정 채팅방으로 전송
 * - 클라이언트가 "/pub/chat/message" 경로로 메시지를 발행하면, `message` 메서드가 호출되어 메시지가 로그에 출력되고 Kafka 토픽에 전송
 * - STOMP를 통해 메시지가 "/sub/chat/room/{roomId}" 경로로 전송되어 해당 채팅방에 있는 모든 클라이언트에게 전달
 * - Kafka Producer를 통해 메시지가 Kafka 토픽에 전송
 *
 * 사용자가 웹소켓 연결되고 나면 이 컨트롤러를 통해 메시지를 전송한다.
 */
