package com.umc.chat.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "participant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Participant {
    @Id
    private Long id;
    private String roomId;
    private Long memberId;

    @Builder
    public Participant(String roomId, Long memberId) {
        this.roomId = roomId;
        this.memberId = memberId;
    }
}
