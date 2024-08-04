package com.umc.chat.taxiroomeventhandler;

import com.umc.common.kafkamessage.chatroomevent.TaxiRoomCreatedEvent;
import com.umc.common.kafkamessage.chatroomevent.TaxiRoomDeletedEvent;
import com.umc.chat.domain.service.ChatRoomService;
import com.umc.chat.domain.service.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class TaxiSharingRoomEventHandlerTest {

    @Mock
    private ChatRoomService chatRoomService;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private TaxiSharingRoomEventHandler eventHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleRoomCreated() {
        // Given
        TaxiRoomCreatedEvent event = TaxiRoomCreatedEvent.builder()
                .roomId("room1")
                .creatorMemberId(1L)
                .build();

        // When
        eventHandler.handleRoomCreated(event);

        // Then
        verify(chatRoomService).createChatRoom(event.getRoomId());
        verify(participantService).createParticipant(event.getRoomId(), event.getCreatorMemberId());
    }

    @Test
    void testHandleRoomDeleted() {
        // Given
        TaxiRoomDeletedEvent event = TaxiRoomDeletedEvent.builder()
                .roomId("room1")
                .build();

        // When
        eventHandler.handleRoomDeleted(event);

        // Then
        verify(participantService).deleteParticipantsByRoomId(event.getRoomId());
        verify(chatRoomService).deleteChatRoom(event.getRoomId());
    }
}