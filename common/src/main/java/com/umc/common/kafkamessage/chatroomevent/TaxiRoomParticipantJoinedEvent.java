package com.umc.common.kafkamessage.chatroomevent;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaxiRoomParticipantJoinedEvent {
    private String roomId;
    private Long joinedMemberId;

    @Builder
    public TaxiRoomParticipantJoinedEvent(String roomId, Long joinedMemberId) {
        this.roomId = roomId;
        this.joinedMemberId = joinedMemberId;
    }
}
