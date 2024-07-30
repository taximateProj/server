package com.umc.chat.taxiroomeventhandler;

import com.umc.common.kafkamessage.chatroomevent.TaxiRoomParticipantJoinedEvent;
import com.umc.common.kafkamessage.chatroomevent.TaxiRoomParticipantLeftEvent;
import com.umc.chat.domain.service.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class TaxiSharingRoomParticipantEventHandlerTest {

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private TaxiSharingRoomParticipantEventHandler eventHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleParticipantJoined() {
        // Given
        TaxiRoomParticipantJoinedEvent event = TaxiRoomParticipantJoinedEvent.builder()
                .roomId("room1")
                .joinedMemberId(2L)
                .build();

        // When
        eventHandler.handleParticipantJoined(event);

        // Then
        verify(participantService).createParticipant(event.getRoomId(), event.getJoinedMemberId());
    }

    @Test
    void testHandleParticipantLeft() {
        // Given
        TaxiRoomParticipantLeftEvent event = TaxiRoomParticipantLeftEvent.builder()
                .roomId("room1")
                .leftMemberId(2L)
                .build();

        // When
        eventHandler.handleParticipantLeft(event);

        // Then
        verify(participantService).deleteParticipant(event.getRoomId(), event.getLeftMemberId());
    }
}