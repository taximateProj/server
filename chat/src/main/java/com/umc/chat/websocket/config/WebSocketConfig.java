package com.umc.chat.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration // Spring 설정 파일임을 나타냅니다.
@EnableWebSocketMessageBroker // WebSocket 메시지 브로커를 활성화합니다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 메시지 브로커 설정을 구성합니다.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트가 메시지를 발행할 때 사용할 prefix를 설정합니다.
        registry.setApplicationDestinationPrefixes("/publish");
        // 클라이언트가 메시지를 구독할 때 사용할 prefix를 설정합니다.
        registry.enableSimpleBroker("/subscribe");
    }

    // STOMP 엔드포인트를 등록합니다.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // /stomp/chat 엔드포인트를 등록하고, SockJS를 지원하도록 설정합니다.
        registry.addEndpoint("/chat")
                .setAllowedOrigins("http://localhost:8080");// 이 오리진만 허용합니다.
                //.withSockJS(); // SockJS를 사용하여 WebSocket을 폴백으로 지원합니다.
    }
}

/**
 * 이 클래스는 WebSocket 설정을 담당
 * 2. @EnableWebSocketMessageBroker: WebSocket 메시지 브로커를 활성화
 *
 * 주요 역할:
 *
 * - WebSocket 메시지 브로커를 설정하여 클라이언트 간의 메시지 교환을 중개
 * - 클라이언트가 메시지를 발행하고 구독하는 경로를 설정
 *
 * 주요 메서드:
 *
 * 1. configureMessageBroker(MessageBrokerRegistry registry):
 *    - 메시지 브로커를 설정
 *    - 메시지 발행(prefix "/pub") 및 구독(prefix "/sub") 경로를 설정
 *
 * 2. registerStompEndpoints(StompEndpointRegistry registry):
 *    - STOMP 엔드포인트를 등록합니다.
 *    - 클라이언트가 /stomp/chat 엔드포인트를 통해 WebSocket 연결을 설정
 *    - 이 엔드포인트는 SockJS를 지원하여 WebSocket이 지원되지 않는 브라우저에서도 동작할 수 있게 한다.
 *
 * 상호작용 요소:
 *
 * - 클라이언트가 "/pub" 경로로 메시지 발행
 * - 클라이언트가 "/sub" 경로로 메시지 발생.
 * - 클라이언트가 "/stomp/chat" 엔드포인트를 통해 WebSocket 연결을 설정
 * - WebSocket 연결은 "http://localhost:8080" 오리진에서만 허용
 * - SockJS를 사용하여 WebSocket을 지원하지 않는 환경에서도 연결을 유지
 *
 *
 * 사용자는 가장 먼저 이 링크를 통해 웹소켓 연결을 신청한다.
 */