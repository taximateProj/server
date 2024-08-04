package com.umc.taxisharing.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "participant")
public class Participant {
    @Id
    private ObjectId id;

    private final Long memberId;
    private final ObjectId taxiSharingRoomId;
    private final LocalDateTime joinedAt;

    @Builder
    public Participant(Long memberId, ObjectId taxiSharingRoomId){
        this.memberId = memberId;
        this.taxiSharingRoomId = taxiSharingRoomId;
        this.joinedAt = LocalDateTime.now();
    }
}
