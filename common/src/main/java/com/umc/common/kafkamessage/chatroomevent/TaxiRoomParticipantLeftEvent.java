package com.umc.common.kafkamessage.chatroomevent;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaxiRoomParticipantLeftEvent {
    private String roomId;
    private Long leftMemberId;

    @Builder
    public TaxiRoomParticipantLeftEvent(String roomId, Long leftMemberId) {
        this.roomId = roomId;
        this.leftMemberId = leftMemberId;
    }
}
