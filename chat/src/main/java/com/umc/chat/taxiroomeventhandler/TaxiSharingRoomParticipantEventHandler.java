package com.umc.chat.taxiroomeventhandler;

import com.umc.common.kafkamessage.chatroomevent.TaxiRoomParticipantJoinedEvent;
import com.umc.common.kafkamessage.chatroomevent.TaxiRoomParticipantLeftEvent;
import com.umc.chat.domain.service.ParticipantService;
import org.springframework.stereotype.Service;

@Service
public class TaxiSharingRoomParticipantEventHandler {

    private final ParticipantService participantService;


    public TaxiSharingRoomParticipantEventHandler(ParticipantService participantService) {
        this.participantService = participantService;
    }

    public void handleParticipantJoined(TaxiRoomParticipantJoinedEvent event) {
        participantService.createParticipant(event.getRoomId(), event.getJoinedMemberId());
    }

    public void handleParticipantLeft(TaxiRoomParticipantLeftEvent event) {
        participantService.deleteParticipant(event.getRoomId(), event.getLeftMemberId());
    }
}
