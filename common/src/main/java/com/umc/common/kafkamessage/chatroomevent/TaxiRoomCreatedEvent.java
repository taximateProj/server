package com.umc.common.kafkamessage.chatroomevent;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaxiRoomCreatedEvent {
    private String roomId;
    private Long creatorMemberId;

    @Builder
    public TaxiRoomCreatedEvent(String roomId, Long creatorMemberId) {
        this.roomId = roomId;
        this.creatorMemberId = creatorMemberId;
    }
}
