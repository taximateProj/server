package com.umc.chat.taxiroomeventhandler;

import com.umc.common.kafkamessage.chatroomevent.TaxiRoomCreatedEvent;
import com.umc.common.kafkamessage.chatroomevent.TaxiRoomDeletedEvent;
import com.umc.chat.domain.service.ChatRoomService;
import com.umc.chat.domain.service.ParticipantService;
import org.springframework.stereotype.Service;

@Service
public class TaxiSharingRoomEventHandler {
    private final ChatRoomService chatRoomService;
    private final ParticipantService participantService;


    public TaxiSharingRoomEventHandler(ChatRoomService chatRoomService, ParticipantService participantService) {
        this.chatRoomService = chatRoomService;
        this.participantService = participantService;
    }

    public void handleRoomCreated(TaxiRoomCreatedEvent event) {
        chatRoomService.createChatRoom(event.getRoomId());
        participantService.createParticipant(event.getRoomId(), event.getCreatorMemberId());
    }

    public void handleRoomDeleted(TaxiRoomDeletedEvent event) {
        participantService.deleteParticipantsByRoomId(event.getRoomId());
        chatRoomService.deleteChatRoom(event.getRoomId());
    }
}
