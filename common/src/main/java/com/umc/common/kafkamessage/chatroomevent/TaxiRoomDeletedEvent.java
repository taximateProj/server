package com.umc.common.kafkamessage.chatroomevent;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaxiRoomDeletedEvent {
    private String roomId;

    @Builder
    public TaxiRoomDeletedEvent(String roomId) {
        this.roomId = roomId;
    }
}
