package com.umc.chat.taxiroomeventhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.common.kafkamessage.CoreMessageDto;
import com.umc.common.kafkamessage.chatroomevent.TaxiRoomCreatedEvent;
import com.umc.common.kafkamessage.chatroomevent.TaxiRoomDeletedEvent;
import com.umc.common.kafkamessage.chatroomevent.TaxiRoomParticipantJoinedEvent;
import com.umc.common.kafkamessage.chatroomevent.TaxiRoomParticipantLeftEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaChatRoomEventListener {

    private final TaxiSharingRoomEventHandler taxiSharingRoomEventHandler;
    private final TaxiSharingRoomParticipantEventHandler taxiSharingRoomParticipantEventHandler;
    private final ObjectMapper objectMapper;

    public KafkaChatRoomEventListener(TaxiSharingRoomEventHandler taxiSharingRoomEventHandler, TaxiSharingRoomParticipantEventHandler taxiSharingRoomParticipantEventHandler,
                                      ObjectMapper objectMapper) {
        this.taxiSharingRoomEventHandler = taxiSharingRoomEventHandler;
        this.taxiSharingRoomParticipantEventHandler = taxiSharingRoomParticipantEventHandler;

        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "taxi-pooling-room-events", groupId = "chat-group")
    public void handleTaxiRoomEvent(@Payload String message) {
        try {
            CoreMessageDto<?> messageDto = objectMapper.readValue(message, CoreMessageDto.class);
            String eventType = messageDto.getEventType();
            // 이벤트 타입에 따라 처리할 메서드를 호출
            switch (eventType) {
                case "ROOM_CREATED":
                    TaxiRoomCreatedEvent roomCreatedEvent = objectMapper.convertValue(messageDto.getData(), TaxiRoomCreatedEvent.class);
                    taxiSharingRoomEventHandler.handleRoomCreated(roomCreatedEvent);
                    break;
                case "ROOM_DELETED":
                    TaxiRoomDeletedEvent roomDeletedEvent = objectMapper.convertValue(messageDto.getData(), TaxiRoomDeletedEvent.class);
                    taxiSharingRoomEventHandler.handleRoomDeleted(roomDeletedEvent);
                    break;
                default:
                    handleUnknownEvent(message, eventType);
                    break;
            }
        } catch (Exception e) {
            // 에러 처리
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "taxi-pooling-room-participant-events", groupId = "chat-group")
    public void handleTaxiRoomParticipantEvent(@Payload String message) {
        try {
            // 메시지를 객체로 변환
            CoreMessageDto<?> messageDto = objectMapper.readValue(message, CoreMessageDto.class);
            String eventType = messageDto.getEventType();

            // 이벤트 타입에 따라 처리할 메서드를 호출
            switch (eventType) {
                case "PARTICIPANT_JOINED_ROOM":
                    TaxiRoomParticipantJoinedEvent joinedEvent = objectMapper.convertValue(messageDto.getData(), TaxiRoomParticipantJoinedEvent.class);
                    taxiSharingRoomParticipantEventHandler.handleParticipantJoined(joinedEvent);
                    break;
                case "PARTICIPANT_LEFT_ROOM":
                    TaxiRoomParticipantLeftEvent leftEvent = objectMapper.convertValue(messageDto.getData(), TaxiRoomParticipantLeftEvent.class);
                    taxiSharingRoomParticipantEventHandler.handleParticipantLeft(leftEvent);
                    break;
                default:
                    handleUnknownEvent(message, eventType);
                    break;
            }
        } catch (Exception e) {
            // 에러 처리
            e.printStackTrace();
        }
    }

    private void handleUnknownEvent(String message, String eventType) {
        log.error("Unknown event type received: {}", eventType);
        // 추가 로깅이나 모니터링을 위한 처리 로직 추가
    }
}
