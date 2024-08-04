package com.umc.chat.intergrationtest;

import com.umc.chat.domain.dto.ChatMessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext
class WebSocketIntegrationTest {

    @LocalServerPort
    private int port;

    private StompSession stompSession;
    private BlockingQueue<ChatMessageDto> blockingQueue;

    @BeforeEach
    public void setup() throws Exception {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        this.blockingQueue = new LinkedBlockingDeque<>();

        String url = "ws://localhost:" + port + "/chat";
        this.stompSession = stompClient.connect(
                url,
                new StompSessionHandlerAdapter() {
                    @Override
                    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                        stompSession = session;
                    }
                }
        ).get(5, TimeUnit.SECONDS);
    }

    @Test
    void testWebSocketConnectionAndMessaging() throws Exception {
        stompSession.subscribe("/subscribe/chat/room/room1", new TestStompFrameHandler());

        ChatMessageDto chatMessage = ChatMessageDto.builder()
                .roomId("room1")
                .sender("user1")
                .content("Hello, World!")
                        .build();

        stompSession.send("/publish/chat/message", chatMessage);

        ChatMessageDto receivedMessage = blockingQueue.poll(5, TimeUnit.SECONDS);
        assertThat(receivedMessage).isNotNull();
        assertThat(receivedMessage.getContent()).isEqualTo("Hello, World!");
        assertThat(receivedMessage.getRoomId()).isEqualTo("room1");
        assertThat(receivedMessage.getSender()).isEqualTo("user1");
    }

    private class TestStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders headers) {
            return ChatMessageDto.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            blockingQueue.offer((ChatMessageDto) payload);
        }
    }
}